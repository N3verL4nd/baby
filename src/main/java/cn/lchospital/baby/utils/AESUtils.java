package cn.lchospital.baby.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密工具类
 *
 * @author liguanghui02
 * @date 2020/9/3
 */
public class AESUtils {

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private AESUtils() {
    }

    /**
     * AES 加密
     *
     * @param str
     * @param key
     * @return 16进制表示
     * @throws Exception
     */
    public static String encrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(bytes);
    }

    /**
     * AES 加密
     *
     * @param str
     * @param key
     * @return 16进制表示
     * @throws Exception
     */
    public static String encrypt(String str, String key, boolean toLowerCase) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(bytes, toLowerCase);
    }

    /**
     * AES 解密
     *
     * @param str 16进制表示
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, String key) throws Exception {
        byte[] bytes = Hex.decodeHex(str);

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String encryptToBase64(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptFromBase64(String str, String key) throws Exception {
        byte[] bytes = Base64.decodeBase64(str);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}