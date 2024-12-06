package cn.example.demo.common.tools.file;

import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 文件导出工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/10/19 17:05
 */
public class ExportFileUtils {
    /**
     * <P>响应文件</P>
     *
     * @param fileData
     * @param response
     * @throws IOException
     */
    public static void responseFile(byte[] fileData, HttpServletResponse response) throws IOException {
        writeByteStream(fileData, response.getOutputStream());
    }

    /**
     * <P>响应文件</P>
     *
     * @param fileData
     * @param response
     * @param fileName
     * @throws IOException
     */
    public static void responseBinaryFile(byte[] fileData, HttpServletResponse response, String fileName) throws IOException {
        // 返回文件流
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + encodedFileName);

        writeByteStream(fileData, response.getOutputStream());
    }

    /**
     * <p>
     * 写入到文件
     * </P>
     *
     * @param fileData
     * @param fileName
     * @throws IOException
     */
    public static void writeToFile(byte[] fileData, String fileName) throws IOException {
        writeToFile(fileData, fileName, false);
    }

    /**
     * <p>
     * 写入到文件
     * </P>
     *
     * @param fileData
     * @param fileName
     * @throws IOException
     */
    public static void writeToFile(byte[] fileData, String fileName, boolean append) throws IOException {
        String resourcePath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath(); // 资源绝对路径（根路径）
        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8.name());  // 解码
        // 获取项目所在的目录
        File file = new File(System.getProperty("user.dir") + File.separator + fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        writeByteStream(fileData, new FileOutputStream(file, append));
    }

    /**
     * <p>
     * 写入字节流对象
     * </P>
     *
     * @param fileData
     * @throws IOException
     */
    protected static void writeByteStream(byte[] fileData, OutputStream os) throws IOException {
        os.write(fileData);
        os.flush();
        os.close();
    }
}
