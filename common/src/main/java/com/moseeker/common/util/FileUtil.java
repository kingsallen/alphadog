package com.moseeker.common.util;

import org.apache.http.Consts;

import java.io.*;

/**
 * @Author: jack
 * @Date: 2018/8/1
 */
public class FileUtil {

    /**
     * 创建文件
     * @param fileName 文件名称
     * @param content 文件内容
     * @return 文件
     * @throws IOException IO异常
     */
    public static File createFile(String fileName, String content) throws IOException{
        File file = new File(fileName);
        ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes(Consts.UTF_8));
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        stream.close();
        return file;
    }

    /**
     * 将文件转成byte数组
     * @param file 文件
     * @return byte数组
     * @throws IOException IO异常
     */
    public static byte[] convertToBytes(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
