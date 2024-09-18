package cn.lchospital.baby.utils;

import org.hashids.Hashids;

/**
 * org.hashids.Hashids 包装类
 * 原理见 https://hashids.org/
 *
 * @author n3verl4nd
 * @date 2020/7/22
 */
public class HashIdUtil {
    /**
     * 默认盐值
     */
    private static final String DEFAULT_SALT = "lchospital.cn";
    /**
     * 加密字符串最小的长度
     */
    private static final int DEFAULT_MIN_LEN = 10;

    /**
     * 将整型转换为字符串
     *
     * @param number
     * @return
     */
    public static String encode(long number) {
        Hashids hashids = new Hashids(DEFAULT_SALT, DEFAULT_MIN_LEN);
        return hashids.encode(number);
    }

    /**
     * 将字符串转换为整型
     *
     * @param hash
     * @return
     */
    public static Long decode(String hash) {
        Hashids hashids = new Hashids(DEFAULT_SALT, DEFAULT_MIN_LEN);
        long[] decode = hashids.decode(hash);
        if (decode == null || decode.length == 0) {
            return null;
        }
        return decode[0];
    }

}