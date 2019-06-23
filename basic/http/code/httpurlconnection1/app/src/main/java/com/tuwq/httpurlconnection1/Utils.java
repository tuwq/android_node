package com.tuwq.httpurlconnection1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String getStringFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baso = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        while((len = inputStream.read(buffer))!=-1){
            baso.write(buffer, 0, len);
        }
        inputStream.close();
        byte[] byteArray = baso.toByteArray();
        return new String(byteArray);
    }

}
