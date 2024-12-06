package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.AnalysisResult;
import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.QueryServiceUtils;
import cn.example.demo.common.tools.file.office.ExcelFileUtils;
import cn.example.demo.common.tools.obj.SimpleStringUtils;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.modules.english.dao.EnglishBookMapper;
import cn.example.demo.modules.english.dao.EnglishWordMapper;
import cn.example.demo.modules.english.model.dto.EnglishBookDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.EnglishWord;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：英语书籍接口实现类
 *
 * @author Lizuxian
 * @create 2021/05/30 00:41:41
 */
@Service
public class EnglishBookServiceImpl implements EnglishBookService {
    @Resource
    private EnglishBookMapper englishBookMapper;
    @Resource
    private EnglishWordMapper englishWordMapper;
    @Resource
    private EnglishWordService englishWordService;

    @Override
    public ServiceResult insertEnglishBook(EnglishBookDto englishBookDto, Sheet sheet) throws Exception {
        List<EnglishWord> englishWords = ExcelFileUtils.excelFileToEntity(EnglishWord.class, sheet);
        if (englishWords == null) {
            return AnalysisResult.isInvalidFormat();
        }
        if (englishWords.isEmpty()) {
            return AnalysisResult.isEmpty();
        }

        // 保存词汇书信息
        EnglishBook englishBook = EntityUtils.entityConvert(englishBookDto, new EnglishBook(), false);
        if (StringUtils.isEmpty(englishBook.getBookCode())) {
            englishBook.setBookCode(SimpleStringUtils.getRandomId());
        }
        if (StringUtils.isEmpty(englishBook.getBookName())) {
            String bookName = sheet.getRow(0).getCell(0).getStringCellValue();
            englishBook.setBookName(StringUtils.isEmpty(bookName) ? "未命名" : bookName);

        }
        englishBook.setFilePath("data/book/" + englishBook.getBookName() + "#" + englishBook.getBookCode() + ".xlsx");
        englishBook.setStatus((short) 1);

        int row = englishBookMapper.insert(englishBook);
        if (row == 1) {
            // TODO：保存英语单词到词汇表，并生成发音文件【多线程执行】
            {
                ServiceResult serviceResult = englishWordService.batchSaveEnglishWord(englishBook, englishWords);
//                System.out.println(serviceResult);
            }

            sheet.getRow(0).getCell(0).setCellValue(englishBook.getBookName());
            return ServiceResult.isSuccess("英语词汇书上传成功!", englishBook);
        }
        return ServiceResult.isInternalError("英语词汇书上传失败，未知错误！");
    }

    @Override
    public ServiceResult updateEnglishBook(EnglishBookDto englishBookDto) {
        EnglishBook englishBook = EntityUtils.entityConvert(englishBookDto, new EnglishBook(), true);
        englishBook.setId(englishBookDto.getId());

        int row = englishBookMapper.updateById(englishBook);
        if (row == 1) {
            return ServiceResult.isSuccess("英语书籍修改成功！", null);
        }
        return ServiceResult.isNotModified("未知错误，英语书籍修改失败！");
    }

    @Override
    public PageBean queryEnglishBook(Integer id, String bookCode, String bookName, String filePath, String description, Short status, Integer questionNum, PageBean pageBean) {
        LambdaQueryWrapper<EnglishBook> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.eq(EnglishBook::getId, id);
        }
        if (StringUtils.isNotEmpty(bookCode)) {
            wrapper.like(EnglishBook::getBookCode, bookCode);
        }
        if (StringUtils.isNotEmpty(bookName)) {
            wrapper.like(EnglishBook::getBookName, bookName);
        }
        if (StringUtils.isNotEmpty(filePath)) {
            wrapper.like(EnglishBook::getFilePath, filePath);
        }
        if (StringUtils.isNotEmpty(description)) {
            wrapper.like(EnglishBook::getDescription, description);
        }
        if (status != null) {
            wrapper.eq(EnglishBook::getStatus, status);
        }

        Page pageResult = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize())
                .doSelectPage(() -> englishBookMapper.selectList(wrapper));

        return QueryServiceUtils.encapsulatePageBean(pageBean, pageResult);
    }

    @Override
    public List<EnglishBook> queryAllEnglishBook() {
        LambdaQueryWrapper<EnglishBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(EnglishBook::getBookName);
        List<EnglishBook> books = englishBookMapper.selectList(wrapper);

        LambdaQueryWrapper<EnglishWord> wordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        books.forEach(o -> {
            wordLambdaQueryWrapper.eq(EnglishWord::getBookCode, o.getBookCode());
            o.setTotalWords(englishWordMapper.selectCount(wordLambdaQueryWrapper));
            wordLambdaQueryWrapper.clear();
        });
        return books;
    }

    @Override
    public ServiceResult deleteEnglishBook(Integer id) {
        EnglishBook book = englishBookMapper.selectById(id);
        if (book == null) {
            return ServiceResult.isNotModified("英语书籍不存在！");
        }

        // 删除词汇表
        LambdaQueryWrapper<EnglishWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnglishWord::getBookCode, book.getBookCode());
        englishWordMapper.delete(wrapper);
        // 删除书籍
        englishBookMapper.deleteById(id);

        return ServiceResult.isSuccess("已删除英语书籍及关联词汇表，书名: " + book.getBookName(), null);
    }

    @Override
    public EnglishBook findEnglishBookById(Integer id) {
        EnglishBook book = englishBookMapper.selectById(id);

        LambdaQueryWrapper<EnglishWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnglishWord::getBookCode, book.getBookCode());

        // 词汇量
        book.setTotalWords(englishWordMapper.selectCount(wrapper));
        // 其他练习板块题量
        book.setBasePracticeNum(book.getBasePracticeNum() == null ? book.getTotalWords() : book.getBasePracticeNum());
        book.setTranslatePracticeNum(book.getTranslatePracticeNum() == null ? book.getTotalWords() : book.getTranslatePracticeNum());
        book.setExamNum(book.getExamNum() == null ? book.getTotalWords() : book.getExamNum());

        return id == null ? null : book;
    }

    @Override
    public boolean isExistBookCode(String bookCode) {
        LambdaQueryWrapper<EnglishBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnglishBook::getBookCode, bookCode);
        return englishBookMapper.selectOne(wrapper) == null ? false : true;
    }
}