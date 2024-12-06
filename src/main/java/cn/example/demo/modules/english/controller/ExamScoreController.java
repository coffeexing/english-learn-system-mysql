package cn.example.demo.modules.english.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.tools.obj.DateAgeUtils;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.english.model.dto.ExamScoreDto;
import cn.example.demo.modules.english.service.ExamScoreService;
import cn.example.demo.modules.sys.model.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 成绩单控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021/05/30 00:20:59
 */
@Validated
@RestController
@RequestMapping("english/api/score")
@Api(tags = {"成绩单操作接口"})
public class ExamScoreController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private ExamScoreService examScoreService;

    @AuthEnable
    @Transactional
    @PostMapping("add")
    @ApiOperation(value = "新增成绩单")
    public HttpResponseResult addExamScore(@RequestBody @Validated(Insert.class) ExamScoreDto dto,
                                           HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        // 保存ExamScore
        ServiceResult result = examScoreService.insertExamScore(dto, user);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "修改成绩单")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyExamScore(@RequestBody @Validated(Update.class) ExamScoreDto dto) {
        // 保存消息通知信息
        ServiceResult result = examScoreService.updateExamScore(dto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("成绩单【" + dto.getId() + "】已修改！", null);
        }

        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "查询成绩单")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getExamScoreList(@RequestParam(defaultValue = "") String bookCode,
                                               @RequestParam(defaultValue = "") String startDate,
                                               @RequestParam(defaultValue = "") String endDate,
                                               @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") @ParamRegex.Integer String page,
                                               @ApiParam(value = "每页大小") @RequestParam(value = "limit", defaultValue = "10") @ParamRegex.Integer String limit,
                                               HttpServletRequest request) {
        // 分页条件
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(limit));

        Date start = StringUtils.isNotEmpty(startDate) ? DateAgeUtils.stringToDate(startDate) : null;
        Date end = StringUtils.isNotEmpty(endDate) ? DateAgeUtils.stringToDate(endDate) : null;

        PageBean result;
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        if (user.getUsername().equals("SuperAdmin") || user.getRoleIds().contains(1)) {
            result = examScoreService.queryExamScore(null, bookCode, start, end, pageBean);
        } else {
            result = examScoreService.queryExamScore(user.getUserId(), bookCode, start, end, pageBean);
        }


        if (result.getItems() == null || result.getItems().isEmpty()) {
            return HttpDataResponseResult.isNotFound("未查到记录！", null);
        }
        return HttpDataResponseResult.isSuccess(result);
    }

    @AuthEnable
    @ApiOperation(value = "删除成绩单")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteExamScore(@PathVariable @ParamRegex.Integer String id) {
        ServiceResult result = examScoreService.deleteExamScore(Integer.valueOf(id));

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "查询个人成绩概览")
    @GetMapping(value = "own/overview")
    public HttpResponseResult getOwnScoreOverview(HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        ServiceResult result = examScoreService.getOverviewData(user);

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }
}
