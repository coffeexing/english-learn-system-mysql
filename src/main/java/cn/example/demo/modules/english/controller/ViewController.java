package cn.example.demo.modules.english.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.validation.constraint.ParamRegex;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.EnglishWord;
import cn.example.demo.modules.english.model.entity.ExamScore;
import cn.example.demo.modules.english.service.EnglishBookService;
import cn.example.demo.modules.english.service.EnglishWordService;
import cn.example.demo.modules.english.service.ExamScoreService;
import cn.example.demo.modules.sys.model.entity.SysUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 英语视图控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021/05/30 00:20:37
 */
@Validated
@Controller("englishViewController")
@Api(tags = {"英语模板视图"})
public class ViewController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private EnglishBookService englishBookService;
    @Resource
    private EnglishWordService englishWordService;
    @Resource
    private ExamScoreService examScoreService;

    // ------------------------------------------ 首页看板 --------------------------------------
    @AuthEnable
    @GetMapping(value = "english/dashboard")
    public Object getDashboardPage(Model model, HttpServletRequest request) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        ServiceResult result = examScoreService.getOverviewData(user);

        if (result.getStatus() == HttpStatus.OK.value()) {
            Map<String, Object> data = (Map) result.getData();

            model.addAttribute("overview", data);
        }

        return "english/dashboard";
    }

    // ------------------------------------------ 英语书籍管理 --------------------------------------
    @AuthEnable
    @GetMapping(value = "english/book/main")
    public Object getEnglishBookManagePage(HttpServletRequest request, Model model) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        boolean auth = false;
        if (user.getUsername().equals("SuperAdmin")) {
            auth = true;
        }

        List<Integer> authRole = Arrays.asList(1, 5); //（角色）
        boolean isPubAndEdit = user.getRoleIds().stream().anyMatch(o -> authRole.contains(o)); // 授权

        model.addAttribute("isPubAndEdit", isPubAndEdit);
        model.addAttribute("auth", auth);

        return "english/book/main";
    }

    @AuthEnable
    @GetMapping(value = "english/book/add")
    public Object getEnglishBookAddPage() {
        return "english/book/add";
    }

    @AuthEnable
    @GetMapping(value = "english/book/edit/{id}")
    public Object getEnglishBookEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        EnglishBook entity = englishBookService.findEnglishBookById(Integer.valueOf(id));
        if (entity != null) {
            model.addAttribute("book", entity);
            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "english/book/edit";
    }

    // ------------------------------------------ 英语词汇管理 --------------------------------------
    @AuthEnable
    @GetMapping(value = "english/word/main")
    public Object getEnglishWordManagePage(HttpServletRequest request, Model model) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        boolean auth = false;
        if (user.getUsername().equals("SuperAdmin")) {
            auth = true;
        }

        List<Integer> authRole = Arrays.asList(1, 5); //（角色）
        boolean isPubAndEdit = user.getRoleIds().stream().anyMatch(o -> authRole.contains(o)); // 授权

        model.addAttribute("isPubAndEdit", isPubAndEdit);
        model.addAttribute("auth", auth);

        return "english/word/main";
    }

    @AuthEnable
    @GetMapping(value = "english/word/add")
    public Object getEnglishWordAddPage() {
        return "english/word/add";
    }

    @AuthEnable
    @GetMapping(value = "english/word/edit/{id}")
    public Object getEnglishWordEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        EnglishWord entity = englishWordService.findEnglishWordById(Integer.valueOf(id));
        if (entity != null) {
            model.addAttribute("id", entity.getId());
            model.addAttribute("bookCode", entity.getBookCode());
            model.addAttribute("wordType", entity.getWordType());
            model.addAttribute("word", entity.getWord());
            model.addAttribute("chinese", entity.getChinese());
            model.addAttribute("pronounceFile", entity.getPronounceFile());
            model.addAttribute("description", entity.getDescription());
            model.addAttribute("status", entity.getStatus());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "english/word/edit";
    }

    // ------------------------------------------ 英语测试成绩管理 --------------------------------------
    @AuthEnable
    @GetMapping(value = "english/score/main")
    public Object getExamScoreManagePage(HttpServletRequest request, Model model) {
        SysUser user = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());

        List<Integer> authRole = Arrays.asList(1); //（角色）
        boolean b = user.getRoleIds().stream().anyMatch(o -> authRole.contains(o)); // 授权

        boolean auth = false;
        if (user.getUsername().equals("SuperAdmin") || b) {
            auth = true;
        }

        model.addAttribute("auth", auth);

        return "english/score/main";
    }

    @AuthEnable
    @GetMapping(value = "english/score/edit/{id}")
    public Object getExamScoreEditPage(@PathVariable("id") @ParamRegex.Integer String id, Model model) {
        ExamScore entity = examScoreService.findExamScoreById(Integer.valueOf(id));
        if (entity != null) {
            model.addAttribute("id", entity.getId());
            model.addAttribute("userId", entity.getUserId());
            model.addAttribute("bookCode", entity.getBookCode());
            model.addAttribute("score", entity.getScore());
            model.addAttribute("point", entity.getPoint());
            model.addAttribute("examTime", entity.getExamTime());

            model.addAttribute("exist", true);
        } else {
            model.addAttribute("exist", false);
        }
        return "english/score/edit";
    }

    // ------------------------------------------ 英语学习板块 --------------------------------------
    @AuthEnable
    @GetMapping(value = "english/study/main")
    public Object getEnglishStudyMainPage(Model model) {
        List<EnglishBook> list = englishBookService.queryAllEnglishBook();

        model.addAttribute("books", list);
        return "english/study/main";
    }

    @AuthEnable
    @GetMapping(value = "english/study/panel/{bookId}")
    public Object getEnglishStudyPanelPage(@PathVariable("bookId") @NotEmpty String bookId, Model model) {
        EnglishBook book = englishBookService.findEnglishBookById(Integer.valueOf(bookId));

        model.addAttribute("book", book == null ? new EnglishBook() : book);

        return "english/study/panel";
    }

    @AuthEnable
    @GetMapping(value = "english/study/base/{bookId}/{bookCode}")
    public Object getEnglishStudyBasePage(@PathVariable("bookId") @NotEmpty String bookId,
                                          @PathVariable("bookCode") @NotEmpty String bookCode,
                                          Model model) {
        EnglishBook book = englishBookService.findEnglishBookById(Integer.valueOf(bookId));
        List<EnglishWord> words = englishWordService.queryAllEnglishWord(bookCode, book.getBasePracticeNum());
        model.addAttribute("words", words);
        model.addAttribute("total", words.size());

        return "english/study/base";
    }

    @AuthEnable
    @GetMapping(value = "english/study/translate/{bookId}/{bookCode}")
    public Object getEnglishStudyTranslatePage(@PathVariable("bookId") @NotEmpty String bookId,
                                               @PathVariable("bookCode") @NotEmpty String bookCode,
                                               Model model) {
        EnglishBook book = englishBookService.findEnglishBookById(Integer.valueOf(bookId));
        List<EnglishWord> words = englishWordService.queryAllEnglishWord(bookCode, book.getTranslatePracticeNum());
        model.addAttribute("words", words);
        model.addAttribute("total", words.size());

        return "english/study/translate";
    }

    @AuthEnable
    @GetMapping(value = "english/study/comprehensive/{bookId}/{bookCode}")
    public Object getEnglishStudyComprehensivePage(@PathVariable("bookId") @NotEmpty String bookId,
                                                   @PathVariable("bookCode") @NotEmpty String bookCode,
                                                   Model model) {
        EnglishBook book = englishBookService.findEnglishBookById(Integer.valueOf(bookId));
        List<EnglishWord> words = englishWordService.queryAllEnglishWord(bookCode, book.getExamNum());
        model.addAttribute("words", words);
        model.addAttribute("total", words.size());
        model.addAttribute("bookCode", bookCode);

        return "english/study/comprehensive";
    }
}
