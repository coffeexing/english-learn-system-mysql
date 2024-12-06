package cn.example.demo.modules.english.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.tools.file.ExportFileUtils;
import cn.example.demo.common.tools.file.SimpleFileUtils;
import cn.example.demo.common.tools.file.office.ExcelFileUtils;
import cn.example.demo.common.tools.obj.DateAgeUtils;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.common.validation.groups.Update;
import cn.example.demo.modules.english.model.dto.EnglishBookDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.EnglishWord;
import cn.example.demo.modules.english.service.EnglishBookService;
import cn.example.demo.modules.sys.model.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 英语书籍控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021/05/30 00:20:37
 */
@Validated
@RestController
@RequestMapping("english/api/book")
@Api(tags = {"英语书籍操作接口"})
public class EnglishBookController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private EnglishBookService englishBookService;

    @AuthEnable
    @Transactional
    @PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "导入英语词汇书")
    public HttpResponseResult addEnglishBook(@ApiParam(value = "英语词汇文件") @RequestParam(value = "file") MultipartFile file,
                                             @ApiParam(value = "书籍编码") @RequestParam(defaultValue = "") String bookCode,
                                             @ApiParam(value = "书籍名称") @RequestParam(defaultValue = "") String bookName,
                                             @ApiParam(value = "描述") @RequestParam(defaultValue = "") String description) throws Exception {
        if (file == null) {
            return HttpDataResponseResult.isBadRequest("英语词汇文件不能为空！", null);
        }
        if (StringUtils.isNotEmpty(bookCode) && englishBookService.isExistBookCode(bookCode)) {
            return HttpDataResponseResult.isBadRequest("该书籍编号已存在！");
        }

        EnglishBookDto dto = EnglishBookDto.builder()
                .bookCode(bookCode)
                .bookName(bookName)
                .description(description).build();

        try {
            InputStream in = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(in);
            // 保存EnglishBook
            ServiceResult result = englishBookService.insertEnglishBook(dto, workbook.getSheetAt(0));

            if (result.getStatus() == HttpStatus.OK.value()) {
                EnglishBook englishBook = (EnglishBook) result.getData();
                // 备份词汇书文件到本地
                ByteArrayOutputStream baOs = new ByteArrayOutputStream();
                workbook.write(baOs);
                ExportFileUtils.writeToFile(baOs.toByteArray(), englishBook.getFilePath());

                return HttpDataResponseResult.isSuccess("英语词汇书籍【" + englishBook.getBookName() + "】上传成功！", englishBook);
            }

            return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());

        } catch (IOException e) {
            return HttpDataResponseResult.isBadRequest("无法将Excel文件解析为内置对象，仅支持xls、xlsx格式的文件。Error ------> " + e.getMessage(), null);
        } catch (POIXMLException e) {
            return HttpDataResponseResult.isBadRequest("无法将Excel文件解析为内置对象，仅支持xls、xlsx格式的文件。Error ------> " + e.getMessage(), null);
        }
    }

    @AuthEnable
    @ApiOperation(value = "修改英语书籍")
    @PutMapping(value = "modify")
    @Transactional
    public HttpResponseResult modifyEnglishBook(@RequestBody @Validated(Update.class) EnglishBookDto dto) {
        // 保存消息通知信息
        ServiceResult result = englishBookService.updateEnglishBook(dto);
        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess("英语书籍【" + dto.getId() + "】已修改！", null);
        }

        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @AuthEnable
    @ApiOperation(value = "查询英语书籍")
    @GetMapping(value = "retrieval")
    public HttpResponseResult getEnglishBookList(
            @RequestParam(defaultValue = "") Integer id,
            @RequestParam(defaultValue = "") String bookCode,
            @RequestParam(defaultValue = "") String bookName,
            @RequestParam(defaultValue = "") String filePath,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") Short status,
            @RequestParam(defaultValue = "") Integer questionNum,
            @ApiParam(value = "当前页") @RequestParam(value = "page", defaultValue = "1") @ParamRegex.Integer String page,
            @ApiParam(value = "每页大小") @RequestParam(value = "limit", defaultValue = "10") @ParamRegex.Integer String limit,
            HttpServletRequest request) {
        // 分页条件
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setCurrentPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(limit));

        PageBean result;
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        if (user.getUsername().equals("SuperAdmin") || user.getRoleIds().contains(1)) {
        } else {
        }
        result = englishBookService.queryEnglishBook(id, bookCode, bookName, filePath, description, status, questionNum, pageBean);

        if (result.getItems() == null || result.getItems().isEmpty()) {
            return HttpDataResponseResult.isNotFound("未查到记录！", null);
        }
        return HttpDataResponseResult.isSuccess(result);
    }

    @AuthEnable
    @ApiOperation(value = "删除英语书籍")
    @DeleteMapping(value = "remove/{id}")
    @Transactional
    public HttpResponseResult deleteEnglishBook(@PathVariable @ParamRegex.Integer String id) {
        ServiceResult result = englishBookService.deleteEnglishBook(Integer.valueOf(id));

        if (result.getStatus() == HttpStatus.OK.value()) {
            return HttpDataResponseResult.isSuccess(result.getMessage(), result.getData());
        }
        return HttpDataResponseResult.isNotModified(result.getMessage(), result.getData());
    }

    @ApiOperation(value = "【英语词汇书】模板导出")
    @GetMapping(value = "book_word_template/export")
    public HttpResponseResult exportEnglishWordTemplate(HttpServletResponse response) throws Exception {
        List<EnglishWord> result = new ArrayList<>();

        EnglishWord englishWord = EnglishWord.builder()
                .word("hello")
                .chinese("你好；喂")
                .description("1.（用以打招呼或唤起注意）喂，你好。如：Hello, Jim! How are you?嗨！吉姆!你好吗?\r\n" +
                        "2.（用作打电话时的招呼语）喂\r\n" +
                        "3.（表示惊讶等）嘿；啊\r\n" +
                        "4.在跟打招呼用hello是不礼貌的，应该用Hi才是有礼貌的。")
                .build();

        result.add(englishWord);

        // 生成表格并返回
        String title = "英语词汇书（书名）";   // 表头标题
        String fileName = title + "_" + DateAgeUtils.dateToString(new Date(), "yyyyMMdd#HHmmss") + ".xlsx";   // 文件名
        byte[] fileData = ExcelFileUtils.exportAsSheet(EnglishWord.class, result, "英语词汇书", title, false);
        ExportFileUtils.responseBinaryFile(fileData, response, fileName);

        return null;
    }

    @ApiOperation(value = "本地【英语词汇书】下载")
    @GetMapping(value = "english_book/export/{id}")
    public HttpResponseResult exportEnglishBook(@PathVariable @ParamRegex.Integer String id,
                                                HttpServletResponse response) throws Exception {
        EnglishBook englishBook = englishBookService.findEnglishBookById(Integer.valueOf(id));
        if (englishBook != null) {
            File file = new File(System.getProperty("user.dir") + File.separator + englishBook.getFilePath());
            if (file != null) {
                byte[] data = SimpleFileUtils.readFileAsByte(file);
                ExportFileUtils.responseBinaryFile(data, response, file.getName());

                return null;
            }
        }
        return HttpDataResponseResult.isNotFound("没有文件可以下载", null);
    }

    @ApiOperation(value = "【英语词汇书】字典列表")
    @GetMapping(value = "english_book/code_dict/list")
    public HttpResponseResult getEnglishBookDict() throws Exception {
        List<EnglishBook> englishBooks = englishBookService.queryAllEnglishBook();
        if (!englishBooks.isEmpty()) {
            List<HashMap<String, String>> bookCodes = englishBooks.stream().map(o -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("code", o.getBookCode());
                map.put("name", o.getBookName());
                return map;
            }).collect(Collectors.toList());

            return HttpDataResponseResult.isSuccess(bookCodes);
        }

        return HttpDataResponseResult.isNotFound("没有查到书籍编码列表", null);
    }
}
