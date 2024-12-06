package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.modules.english.model.dto.EnglishBookDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * 描述：英语书籍服务接口
 *
 * @author Lizuxian
 * @create 2021/05/30 00:24:57
 */
public interface EnglishBookService {
    /**
     * 新增英语书籍
     *
     * @param englishBookDto
     * @return
     */
    ServiceResult insertEnglishBook(EnglishBookDto englishBookDto, Sheet sheet) throws Exception;

    /**
     * 更新英语书籍
     *
     * @param englishBookDto
     * @return
     */
    ServiceResult updateEnglishBook(EnglishBookDto englishBookDto);

    /**
     * @return
     */
    PageBean queryEnglishBook(Integer id, String bookCode, String bookName, String filePath, String description, Short status, Integer questionNum, PageBean pageBean);

    List<EnglishBook> queryAllEnglishBook();

    /**
     * 删除英语书籍
     *
     * @param id
     */
    ServiceResult deleteEnglishBook(Integer id);

    /**
     * 根据ID查询英语书籍
     *
     * @param id
     */
    EnglishBook findEnglishBookById(Integer id);

    boolean isExistBookCode(String bookCode);
}
