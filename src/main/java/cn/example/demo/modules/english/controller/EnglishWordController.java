package cn.example.demo.modules.english.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.tools.file.ExportFileUtils;
import cn.example.demo.common.tools.file.SimpleFileUtils;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.english.model.dto.EnglishWordDto;
import cn.example.demo.modules.english.model.entity.EnglishWord;
import cn.example.demo.modules.english.service.EnglishWordService;
import cn.example.demo.modules.sys.model.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 英语词库控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021/05/30 00:21:05
 */
@Validated
@RestController
@RequestMapping("english/api/word")
@Api(tags = {"英语词库操作接口"})
public class EnglishWordController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private EnglishWordService englishWordService;

    @AuthEnable
    @Transactional
    @PostMapping("add")
    @ApiOperation(value = "新增英语词库")
    public HttpResponseResult addEnglishWord(@RequestBody @Validated(Insert.class) EnglishWordDto dto,
                                             HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        // 保存EnglishWord
        ServiceResult result = englishWordService.insertEnglishWord(dto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("英语词语【" + dto.getWord() + "】新增成功！", null);
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "修改英语词库")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyEnglishWord(@RequestBody @Validated(Update.class) EnglishWordDto dto) {
        // 保存消息通知信息
        ServiceResult result = englishWordService.updateEnglishWord(dto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("英语词库【" + dto.getId() + "】已修改！", null);
        }

        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "查询英语词库")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getEnglishWordList(@RequestParam(defaultValue = "") String bookCode,
                                                 @RequestParam(defaultValue = "") Short wordType,
                                                 @RequestParam(defaultValue = "") String word,
                                                 @RequestParam(defaultValue = "") String chinese,
                                                 @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") @ParamRegex.Integer String page,
                                                 @ApiParam(value = "每页大小") @RequestParam(value = "limit", defaultValue = "10") @ParamRegex.Integer String limit) {
        // 分页条件
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(limit));

        PageBean result = englishWordService.queryEnglishWord(bookCode, wordType, word, chinese, pageBean);

        if (result.getItems() == null || result.getItems().isEmpty()) {
            return HttpDataResponseResult.isNotFound("未查到记录！", null);
        }
        return HttpDataResponseResult.isSuccess(result);
    }

    @AuthEnable
    @ApiOperation(value = "删除英语词库")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteEnglishWord(@PathVariable @ParamRegex.Integer String id) {
        ServiceResult result = englishWordService.deleteEnglishWord(Integer.valueOf(id));

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "获取单词音频文件")
    @GetMapping(value = "pronounce/{id}")
    public HttpResponseResult getEnglishWordPronounce(@PathVariable @ParamRegex.Integer String id,
                                                      HttpServletResponse response) throws IOException {
        EnglishWord word = englishWordService.findEnglishWordById(Integer.valueOf(id));
        if (word != null) {
            File mp3File = new File(System.getProperty("user.dir") + File.separator + word.getPronounceFile());
            if (mp3File != null && mp3File.exists()) {
                response.setContentType("audio/mpeg");
                response.setCharacterEncoding(null);
                response.setContentLength((int) mp3File.length());
                ExportFileUtils.responseFile(SimpleFileUtils.readFileAsByte(mp3File), response);
                return null;
            }
            return HttpDataResponseResult.isNotFound("单词【" + word.getWord() + "】的音频文件不存在！", null);
        }
        return HttpDataResponseResult.isNotFound("不存在的单词！", null);
    }

    @ApiOperation(value = "单词验证")
    @GetMapping(value = "verify/{wordId}/{word}")
    public HttpResponseResult verifyWord(@PathVariable @ParamRegex.Integer String wordId,
                                         @PathVariable @NotEmpty String word) {
        EnglishWord englishWord = englishWordService.findEnglishWordById(Integer.valueOf(wordId));

        if (englishWord != null) {
            Map<String, Object> result = new HashMap<>();
            if (englishWord.getWord().equals(word)) {
                result.put("code", 1);
                result.put("msg", "作答正确！");
            } else if (word.equals("-")) {
                result.put("code", -1);
                result.put("msg", "超时作答！");
            } else {
                result.put("code", 0);
                result.put("msg", "作答错误！");
            }
            result.put("yourAnswer", word);
            result.put("trueAnswer", englishWord.getWord());

            return HttpDataResponseResult.isSuccess(result);
        }

        return HttpDataResponseResult.isNotFound("词语【" + word + "】不存在！", null);
    }
}
