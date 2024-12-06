package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.modules.english.model.dto.EnglishWordDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.EnglishWord;

import java.util.List;

/**
 * 描述：英语词库服务接口
 *
 * @author Lizuxian
 * @create 2021/05/30 00:24:48
 */
public interface EnglishWordService {
    /**
     * 新增英语词库
     *
     * @param englishWordDto
     * @return
     */
    ServiceResult insertEnglishWord(EnglishWordDto englishWordDto);

    ServiceResult batchSaveEnglishWord(EnglishBook englishBook, List<EnglishWord> englishWords);

    /**
     * 更新英语词库
     *
     * @param englishWordDto
     * @return
     */
    ServiceResult updateEnglishWord(EnglishWordDto englishWordDto);

    /**
     * @return
     */
    PageBean queryEnglishWord(String bookCode, Short wordType, String word, String chinese, PageBean pageBean);

    List<EnglishWord> queryAllEnglishWord(String bookCode, Integer questionNum);

    /**
     * 删除英语词库
     *
     * @param id
     */
    ServiceResult deleteEnglishWord(Integer id);

    /**
     * 根据ID查询英语词库
     *
     * @param id
     */
    EnglishWord findEnglishWordById(Integer id);
}
