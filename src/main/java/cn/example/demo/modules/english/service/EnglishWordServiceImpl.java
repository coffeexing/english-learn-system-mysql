package cn.example.demo.modules.english.service;

import cn.example.demo.common.model.service.ServiceResult;
import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.QueryServiceUtils;
import cn.example.demo.common.tools.file.ExportFileUtils;
import cn.example.demo.common.tools.net.HttpClientUtils;
import cn.example.demo.common.tools.obj.reflect.EntityUtils;
import cn.example.demo.modules.english.dao.EnglishWordMapper;
import cn.example.demo.modules.english.model.dto.EnglishWordDto;
import cn.example.demo.modules.english.model.entity.EnglishBook;
import cn.example.demo.modules.english.model.entity.EnglishWord;
import cn.example.demo.modules.english.model.entity.EnglishWordExample;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：英语词库接口实现类
 *
 * @author Lizuxian
 * @create 2021/05/30 00:41:38
 */
@Service
public class EnglishWordServiceImpl implements EnglishWordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnglishWordServiceImpl.class);
    /**
     * English Pattern
     */
    private static final Pattern WORD_PATTERN = Pattern.compile("\\w+");
    /**
     * TODO-lZX 要翻墙才可以访问了
     * 全球英语单词发音地址（前缀）
     */
    private static final String GLOBAL_PRONOUNCE_URL_PREFIX = "https://ssl.gstatic.com/dictionary/static/sounds/oxford/";
    /**
     * 全球英语单词发音地址（后缀）
     */
    private static final String GLOBAL_PRONOUNCE_URL_SUFFIX = "--_gb_1.mp3";
    /**
     * 发音文件类型后缀
     */
    private static final String PRONOUNCE_AUDIO_FILE_SUFFIX = ".wav";
    @Resource
    private EnglishWordMapper englishWordMapper;

    /**
     * 全局变量设置， FreeTTS 使用 Kevin 语音
     */
    {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }
    @Override
    public ServiceResult insertEnglishWord(EnglishWordDto englishWordDto) {
        EnglishWord word = EntityUtils.entityConvert(englishWordDto, new EnglishWord(), false);
        word.setBookCode(word.getBookCode());
        word.setStatus((short) 1);
        wrapCommonAttr(word);
        // 保存信息到数据库
        int row = englishWordMapper.insert(word);
        if (row == 1) {
            // 下载发音文件
            generateWordVoiceFile(Arrays.asList(word));
            return ServiceResult.isSuccess("英语词库更新成功!", word);
        }
        return ServiceResult.isInternalError("英语词库创建失败，未知错误！");
    }

    @Override
    public ServiceResult batchSaveEnglishWord(EnglishBook englishBook, List<EnglishWord> englishWords) {
        // 数据封装
        englishWords.forEach(word -> {
            word.setBookCode(word.getBookCode());
            word.setBookCode(englishBook.getBookCode());
            word.setStatus((short) 1);
            wrapCommonAttr(word);
        });
        int rows = englishWordMapper.batchInsert(englishWords);
        if (rows != 0) {
            // 下载音频文件
            generateWordVoiceFile(englishWords);
            return ServiceResult.isSuccess("词汇批量保存生成！更新记录数：" + rows, englishWords);
        }
        return ServiceResult.isNotModified("词汇批量保存失败!");
    }

    @Override
    public ServiceResult updateEnglishWord(EnglishWordDto englishWordDto) {
        EnglishWord englishWord = EntityUtils.entityConvert(englishWordDto, new EnglishWord(), true);
        englishWord.setId(englishWordDto.getId());
        wrapCommonAttr(englishWord);
        // 保存信息到数据库
        int row = englishWordMapper.updateById(englishWord);
        if (row == 1) {
            // 更新音频文件
            generateWordVoiceFile(Arrays.asList(englishWord));
            return ServiceResult.isSuccess("英语单词【" + englishWord.getWord() + "】+修改成功！", null);
        }
        return ServiceResult.isNotModified("未知错误，英语词库修改失败！");
    }

    @Override
    public PageBean queryEnglishWord(String bookCode, Short wordType, String word, String chinese, PageBean pageBean) {
        EnglishWordExample wordExample = new EnglishWordExample();
        EnglishWordExample.Criteria criteria = wordExample.createCriteria();

        if (StringUtils.isNotEmpty(bookCode)) {
            criteria.andBookCodeEqualTo(bookCode);
        }
        if (wordType != null) {
            criteria.andWordTypeEqualTo(wordType);
        }
        if (StringUtils.isNotEmpty(word)) {
            criteria.andWordLike("%" + word + "%");
        }
        if (StringUtils.isNotEmpty(chinese)) {
            criteria.andChineseLike("%" + chinese + "%");
        }
        Page pageResult = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize())
                .doSelectPage(() -> englishWordMapper.selectAssociateByExample(wordExample));
        return QueryServiceUtils.encapsulatePageBean(pageBean, pageResult);
    }

    @Override
    public List<EnglishWord> queryAllEnglishWord(String bookCode, Integer questionNum) {
        LambdaQueryWrapper<EnglishWord> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(bookCode)) {
            wrapper.eq(EnglishWord::getBookCode, bookCode);
        }
        List<EnglishWord> words = englishWordMapper.selectList(wrapper);
        if (!words.isEmpty()) {
            // 随机排列
            Collections.shuffle(words);
            return words.subList(0, questionNum > words.size() ? words.size() : questionNum);
        }
        return words;
    }

    @Override
    public ServiceResult deleteEnglishWord(Integer id) {
        int row = englishWordMapper.deleteById(id);
        if (row == 1) {
            return ServiceResult.isSuccess("已删除英语词库，ID: " + id, null);
        }
        return ServiceResult.isNotModified("删除英语词库失败，ID: " + id);
    }

    @Override
    public EnglishWord findEnglishWordById(Integer id) {
        return id == null ? null : englishWordMapper.selectById(id);
    }

    /**
     * <p>
     * 音频文件下载（另开线程）
     * </P>
     *
     * @param englishWords
     */
    void downLoadWordsMp3(List<EnglishWord> englishWords) {
        // 下载音频文件
        Thread thread = new Thread(() -> englishWords.forEach(word -> {
            // 解析的单词不为空
            if (word.getMatchWord() != null) {
                try {
                    byte[] bytes = HttpClientUtils.sendDownloadRequest(GLOBAL_PRONOUNCE_URL_PREFIX + word.getMatchWord() + GLOBAL_PRONOUNCE_URL_SUFFIX);
                    ExportFileUtils.writeToFile(bytes, word.getPronounceFile());
                    LOGGER.info("单词【" + word.getMatchWord() + "】发音（英式）文件下载成功！");
                } catch (IOException e) {
                    LOGGER.error("单词【" + word.getMatchWord() + "】发音文件下载失败；错误消息：" + e.getMessage());
                    // 单词发音文件位置设置为为空
                    LambdaUpdateWrapper<EnglishWord> wrapper = new LambdaUpdateWrapper<>();
                    wrapper.set(EnglishWord::getPronounceFile, null);
                    wrapper.eq(EnglishWord::getId, word.getId());
                    englishWordMapper.update(word, wrapper);
                    wrapper.clear();
                }
            }
        }));
        // 执行任务
        thread.start();
    }

    /**
     * TODO-lZX
     * 生成单词发音文件（因访问国外词典库网络受限制，暂时使用此方案）
     *
     * @param englishWords
     */
    protected void generateWordVoiceFile(List<EnglishWord> englishWords) {
        // 使用FreeTTS生成音频文件
        Thread thread = new Thread(() -> englishWords.forEach(word -> {
            // 解析的单词不为空
            if (word.getMatchWord() != null) {
                try {
                    VoiceManager voiceManager = VoiceManager.getInstance();
                    Voice voice = voiceManager.getVoice("kevin16");
                    if (voice == null) {
                        System.out.println("Cannot find a voice named kevin16. Please specify a different voice.");
                        return;
                    }
                    // 音频文件路径
                    String audioFile = System.getProperty("user.dir") + File.separator + word.getPronounceFile().replace(PRONOUNCE_AUDIO_FILE_SUFFIX, "");
                    // 这里需要创建目录，否则音频文件无法写入
                    File file = new File(audioFile);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                    }
                    // 分配一个音频播放器
                    SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer(audioFile, AudioFileFormat.Type.WAVE);
                    voice.setAudioPlayer(audioPlayer);
                    // 初始化声音引擎
                    voice.allocate();
                    // 将文本转换为语音
                    voice.speak(word.getWord());
                    // 释放资源
                    voice.deallocate();
                    audioPlayer.close();
                    LOGGER.info("【{}】发音（英式）文件转换成功！文件路径：{}", word.getWord(), audioFile);
                } catch (Exception e) {
                    LOGGER.error("【{}】发音文件生成失败；错误消息：{}", word.getWord(), e.getMessage());
                    // 单词发音文件位置设置为为空
                    LambdaUpdateWrapper<EnglishWord> wrapper = new LambdaUpdateWrapper<>();
                    wrapper.set(EnglishWord::getPronounceFile, null);
                    wrapper.eq(EnglishWord::getId, word.getId());
                    englishWordMapper.update(word, wrapper);
                    wrapper.clear();
                }
            }
        }));
        // 执行任务
        thread.start();
    }

    /**
     * 封装通用属性
     *
     * @param word
     */
    protected void wrapCommonAttr(EnglishWord word) {
        Matcher matcher = WORD_PATTERN.matcher(word.getWord());
        if (matcher.find()) {
            // 匹配单词格式
            word.setMatchWord(matcher.group().toLowerCase(Locale.US));
            word.setWordType(StringUtils.containsWhitespace(word.getMatchWord()) ? (short) 2 : (short) 1);   // 2: 词组; 1: 单词
            word.setPronounceFile(StrUtil.format("data/pronounce/{}/{}_{}_{}", word.getBookCode(), word.getMatchWord(), RandomUtil.randomInt(65535), PRONOUNCE_AUDIO_FILE_SUFFIX));
        }
    }
}
