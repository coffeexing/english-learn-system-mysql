package cn.example.demo.common.tools.obj;

import cn.example.demo.common.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <P>日期工具类，包含日期转换，年龄计算等</P>
 *
 * @author Lizuxian
 */
public class DateAgeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateAgeUtils.class);

    /**
     * 日期匹配格式。前三个的顺序不能打乱，保证优先级【yyyyMMdd】>【yyyyMM】>【yyyy】
     * （PS:因为 '20201001' 能同时匹配 'yyyyMM'，'yyyy' 这种格式）
     */
    private static String[] pattern = new String[]{
            "yyyyMMdd", "yyyyMM", "yyyy", "yyyy-MM", "yyyy/MM", "yyyy.MM",
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S"};

    /**
     * 日期转换: String to Date
     */
    public static Date stringToDate(String originDate) {
        Date targetDate = null;
        if (StringUtils.isNotEmpty(originDate)) {
            try {
                // 时间戳的形式，转数值型
                if (originDate.matches("^[1-9][0-9]*")) {
                    long longDate = Long.valueOf(originDate.trim());
                    // 长度需大于 ‘20201001’ 等这种格式
                    if (longDate > 99999999l) return new Date(longDate);
                }
                // 其他字符格式
                targetDate = DateUtils.parseDate(originDate, pattern);
            } catch (NumberFormatException e) {
                LOGGER.error(e.getMessage());
                // 输出到日志文件
                ExceptionUtils.outputExceptionMsgToLogFile(new NumberFormatException("字符串（数值型）转日期出错，其他信息：" + e.toString()));

            } catch (ParseException pe) {
                String msg = String.format(
                        "【%s】 can not convert to type 'java.util.Date',just support timestamp(type of long) and following date format【%s】",
                        originDate,
                        StringUtils.join(pattern, ","));
                LOGGER.error(msg);
                // 输出到日志文件
                ExceptionUtils.outputExceptionMsgToLogFile(new Exception("字符串转日期出错！其他信息：" + msg, pe));
            }
        }
        return targetDate;
    }

    /**
     * 日期转换: String to Date
     */
    public static Date stringToDate(String time, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            LOGGER.error("字符串转日期出错!");
            // 输出到日志文件
            ExceptionUtils.outputExceptionMsgToLogFile(new Exception("字符串转日期出错!", e));
        }
        return date;
    }

    /**
     * 日期转换方法: Date to String
     */
    public static String dateToString(Date time, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        String dateStr = "";
        if (time != null) {
            dateStr = dateFormat.format(time);   // 根据特定模式将日期转为特定字符串
        }
        return dateStr;
    }

    /**
     * 日期转换方法: Date to Date。
     * 根据模式将日期转换为另一日期，用于获取 xxxx年yy月1号【例:pattern -> "yyyy-MM"】，或者 xxxx年1月1号【例:pattern -> "yyyy"】等。
     */
    public static Date dateToDate(Date date, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return new Date();
        }
        String s = dateToString(date, pattern);
        return stringToDate(s, pattern);
    }

    /**
     * <p>
     * 增加日期天数
     * </P>
     *
     * @param originDate
     * @param amount
     * @return Date
     */
    public static Date addDayAmountOfDate(Date originDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originDate);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
    }

    /**
     * <p>
     * 增加日期天数
     * </P>
     *
     * @param originDate
     * @param amount
     * @return Date
     */
    public static Date addDayAmountOfDate(String originDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(originDate));
        cal.add(Calendar.DAY_OF_MONTH, amount);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
    }

    /**
     * <p>
     * 增加日期月数
     * </P>
     *
     * @param originDate <type>String</type>
     * @param amount
     * @return Date
     */
    public static Date addMonthAmountOfDate(String originDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateAgeUtils.stringToDate(originDate));
        cal.add(Calendar.MONTH, amount);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
    }

    /**
     * <p>
     * 增加日期年数
     * </P>
     *
     * @param originDate <type>String</type>
     * @param amount
     * @return Date
     */
    public static Date addYearAmountOfDate(String originDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateAgeUtils.stringToDate(originDate));
        cal.add(Calendar.YEAR, amount);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
    }

    /**
     * <p>
     * 增加日期类型增加数量
     * </P>
     *
     * @param originDate
     * @param amount
     * @return Date
     */
    public static Date addAmountOfDate(Date originDate, int calendarCode, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originDate);
        cal.add(calendarCode, amount);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
    }

    /**
     * 根据当前时间与出生年月计算年龄
     */
    public static int getAgeByBirthday(Date birth) {
        int age = 0;  //初始化年龄
        Calendar cal = Calendar.getInstance();
        if (cal.before(birth)) {
            throw new IllegalArgumentException("The birthDay is later than the current Date!");
        }

        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
//        System.out.println("当前日历----- 年：" + currentYear + "  月：" + currentMonth + "  日：" + currentDay);

        cal.setTime(birth);

        int birthYear = cal.get(Calendar.YEAR);
        int birthMonth = cal.get(Calendar.MONTH) + 1;
        int birthDay = cal.get(Calendar.DAY_OF_MONTH);
//        System.out.println("出生日历----- 年：" + birthYear + "  月：" + birthMonth + "  日：" + birthDay);

//        当前年份与出生年份相减
        age = currentYear - birthYear;
//        根据月份与日数判断是否减去 1 岁
        if (currentMonth <= birthMonth) {   // 如果当前月份 <= 出生月份，分别判断 小于 与 相等 两种情况
            if (currentMonth == birthMonth) {   // 月份相等，则判断两个日数的大小
                if (currentDay < birthDay) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * <p>
     * 计算两日期之间相差的天数
     * </P>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int calculateDayAmount(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            if (date1.getTime() > date2.getTime()) {
                return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
            } else {
                return (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
            }
        }
        return 0;
    }
}
