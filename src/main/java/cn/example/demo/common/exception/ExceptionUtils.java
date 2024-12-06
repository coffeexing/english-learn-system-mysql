package cn.example.demo.common.exception;

import cn.example.demo.common.tools.file.ExportFileUtils;
import cn.example.demo.common.tools.obj.DateAgeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 异常处理工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2020-11-19 16:58
 */
public class ExceptionUtils {
    /**
     * <p>
     * 跟踪调用链，生成异常信息
     * </P>
     *
     * @param t
     * @return
     */
    public static String generateThrowableMsg(Throwable t) {
        // AOP 未正常结束时若被拦截的方法没有显式 throws Exception 则会抛出<UndeclaredThrowableException>，这个异常并不需要。
        t = t instanceof UndeclaredThrowableException ? t.getCause() : t;
        Throwable cause = t;
        // 保存每个Cause的Msg
        ArrayList l1;
        for (l1 = new ArrayList(); cause.getCause() != null; cause = cause.getCause()) {
            l1.add(cause.toString());
        }
        l1.add(cause.toString());   // 最后一个Cause（root Cause）
        // 生成异常信息
        StringBuilder msg = new StringBuilder(StringUtils.join(l1, " --->>> "));
        msg.append("\r\n》》》调用栈跟踪：\r\n### ");
        // 保存root Cause的StackTrace
        List<String> l2 = new ArrayList();
        for (StackTraceElement trace : cause.getStackTrace()) {
            l2.add("【类名：" + trace.getClassName() + "；方法名：" + trace.getMethodName() + "；报错行：" + trace.getLineNumber() + "；文件名：" + trace.getFileName() + "】");
        }
        msg.append(StringUtils.join(l2, "\r\n") + " ###");
        return msg.toString();
    }

    /**
     * <p>
     * 输出异常信息到外部日志文件
     * </P>
     *
     * @param t
     */
    public static void outputExceptionMsgToLogFile(Throwable t) {
        Date time = new Date();
        String msg = generateThrowableMsg(t);    // 获取异常信息
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------- 时间：" + DateAgeUtils.dateToString(time, "yyyy-MM-dd HH:mm:ss" + " -------------------------------------------------\r\n"));
        sb.append(msg);
        sb.append("\r\n\r\n");
        try {
            // 输出到文件
            ExportFileUtils.writeToFile(sb.toString().getBytes(StandardCharsets.UTF_8), "logs/error/log_" + DateAgeUtils.dateToString(time, "yyyyMMdd") + ".txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
