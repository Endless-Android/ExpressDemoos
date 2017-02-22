package com.example.administrator.expressdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/22.
 */

public class StringUtils {
    public static String InputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] bt = new byte[1024];
        int len = -1;
        while ((len = is.read(bt))!=-1){
            bao.write(bt,0,len);
        }
        is.close();
    return new String(bao.toByteArray());
    }

}
