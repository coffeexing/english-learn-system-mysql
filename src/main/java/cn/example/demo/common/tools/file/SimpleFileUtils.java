package cn.example.demo.common.tools.file;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-08 7:05
 */
public class SimpleFileUtils {
    /**
     * <p>
     * 读取文件字节流
     * </P>
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readFileAsByte(String filePath) throws IOException {
        FileInputStream fIs = new FileInputStream(filePath);
        return getBytesFromFileInputStream(fIs);
    }

    /**
     * <p>
     * 读取文件字节流
     * </P>
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readFileAsByte(File file) throws IOException {
        FileInputStream fIs = new FileInputStream(file);
        return getBytesFromFileInputStream(fIs);
    }

    /**
     * <p>
     * 格式化文件路径
     * </P>
     *
     * @param filePath
     * @return
     */
    public static String formatFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }

        String[] arr = null;
        if (filePath.contains("/")) {
            arr = StringUtils.split(filePath, "/");
        } else if (filePath.contains("\\")) {
            arr = StringUtils.split(filePath, "\\");
        }

        if (arr != null) {
            String path = StringUtils.join(arr, File.separator);
            return path;
        }

        return filePath;
    }

    /**
     * <p>
     * 获取文件相对路径
     * </P>
     *
     * @param path
     * @return
     */
    public static String getRelativePath(String path) {
        if (StringUtils.isNotEmpty(path)) {
            int i = path.lastIndexOf(File.separator);
            if (i != -1) {
                return path.substring(0, i + 1);
            }
        }

        return path;
    }

    protected static byte[] getBytesFromFileInputStream(FileInputStream fIs) throws IOException {
        ByteArrayOutputStream bOs = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int i;
        while ((i = fIs.read(buffer, 0, buffer.length)) != -1) {
            bOs.write(buffer, 0, i);
        }
        return bOs.toByteArray();
    }
}
