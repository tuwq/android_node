package com.tuwq.htmlcodeviewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {

    /**
     * 流转换为字符串内容
     * @param inputStream
     * @return
     */
    public static String getStringFormInputStream(InputStream inputStream) {
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        try {
            while((len = inputStream.read(buffer)) != -1) {
                baso.write(buffer, 0, len);
            }
            inputStream.close();
            byte[] byteArray = baso.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }
}
