package cn.lchospital.baby.utils;

import com.google.common.base.Charsets;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
/**
* 提供 <CODE>application/x-www-form-urlencoded</CODE> 标准的字符串编解码工具
*
*/
public class URLCodec {
    
    public static String encode(String plainText) {
        return encode(plainText, Charsets.UTF_8);
    }
    
    public static String encode(String plainText, Charset charset) {
        try {
            return URLEncoder.encode(plainText, charset.displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return plainText;
    }
    
    public static String decode(String plainText) {
        return encode(plainText, Charsets.UTF_8);
    }
    
    public static String decode(String plainText, Charset charset) {
        try {
            return URLDecoder.decode(plainText, charset.displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return plainText;
    }
}