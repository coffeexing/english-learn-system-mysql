package cn.example.demo.common.tools.obj;

import java.io.*;
import java.util.Optional;

/**
 * <p>
 * 简单对象工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-23 9:12
 */
public class SimpleObjectUtils {
    /**
     * <p>
     * Java对象转字节数组
     * </P>
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> Optional<byte[]> objectToBytes(T obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = new ObjectOutputStream(out);
        sOut.writeObject(obj);
        sOut.flush();
        byte[] bytes = out.toByteArray();
        return Optional.ofNullable(bytes);
    }

    /**
     * <p>
     * 字节数组转Java对象
     * </P>
     *
     * @param bytes
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> Optional<T> bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        T obj = (T) sIn.readObject();
        return Optional.ofNullable(obj);
    }
}
