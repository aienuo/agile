package com.imis.agile.util;

import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 双向加密
 *
 * @author XinLau
 */
@Slf4j
public class PasswordUtil {

    /**
     * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
     * PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
     * 定义使用的算法为:PBEWITHMD5andDES算法
     */
    public static final String ALGORITHM = "PBEWithMD5AndDES";

    /**
     * 定义迭代次数为1000次
     */
    private static final int ITERATION_COUNT = 1000;

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 加密正则
     */
    private static final Pattern ENC_PATTERN = Pattern.compile("^ENC\\((.*)\\)$");

    /**
     * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
     *
     * @return byte[] 盐值
     */
    public static byte[] getSalt() {
        // 实例化安全随机数
        SecureRandom random = new SecureRandom();
        // 产出盐
        return random.generateSeed(8);
    }

    /**
     * 获取字符串盐
     *
     * @return String
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 16:33
     */
    public static String getStringSalt() {
        return getStringUuid(8);
    }

    /**
     * 获取UUID
     *
     * @return UUID
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 8:59
     */
    public static UUID getUuid() {
        return UUID.randomUUID();
    }

    /**
     * 获取字符串UUID
     *
     * @return String
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 8:59
     */
    public static String getStringUuid() {
        return getUuid().toString().replaceAll(StringPool.DASH, StringPool.EMPTY);
    }

    /**
     * 获取指定长度字符串UUID
     *
     * @param length - 长度
     * @return String
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 8:59
     */
    public static String getStringUuid(final int length) {
        String uuid = getStringUuid();
        if (length < 1) {
            return uuid;
        } else if (length > uuid.length()) {
            uuid += getStringUuid();
        }
        return uuid.substring(0, length);
    }

    /**
     * 获取字符数组盐
     *
     * @return byte[]
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 8:59
     */
    public static byte[] getStaticSalt() {
        // 产出盐
        return getStringSalt().getBytes();
    }

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password 生成密钥时所使用的密码
     * @return Key PBE算法密钥
     */
    private static Key getPbeKey(final String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return secretKey;
    }

    /**
     * 加密明文字符串
     *
     * @param plaintext 待加密的明文字符串
     * @param password  生成密钥时所使用的密码
     * @param salt      盐值
     * @return 加密后的密文字符串
     */
    public static String encrypt(final String plaintext, final String password, final String salt) {
        Key key = getPbeKey(password);
        byte[] encipheredData = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATION_COUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            encipheredData = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return bytesToHexString(encipheredData);
    }

    /**
     * 解密密文字符串
     *
     * @param ciphertext 待解密的密文字符串
     * @param password   生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
     * @param salt       盐值(如需解密,该参数需要与加密时使用的一致)
     * @return 解密后的明文字符串
     */
    public static String decrypt(final String ciphertext, final String password, final String salt) {
        Key key = getPbeKey(password);
        byte[] passDec = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATION_COUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            passDec = cipher.doFinal(hexStringToBytes(ciphertext));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        assert passDec != null;
        return new String(passDec);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param src 字节数组
     * @return String 十六进制字符串
     */
    public static String bytesToHexString(final byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length == 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || StringPool.EMPTY.equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * mybatis-plus 加密
     *
     * @param beforeEncryption - 加密前
     * @return String - 加密后
     */
    public static String encrypt(String beforeEncryption) {
        String afterEncryption = StringPool.EMPTY;
        if (AgileUtil.isNotEmpty(beforeEncryption)) {
            try {
                afterEncryption = CryptoUtils.encrypt(beforeEncryption);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return "ENC(" + afterEncryption + ")";
    }

    /**
     * mybatis-plus 解密
     *
     * @param beforeDecrypt - 解密前
     * @return String - 解密后
     */
    public static String decrypt(String beforeDecrypt) {
        if (AgileUtil.isNotEmpty(beforeDecrypt)) {
            Matcher matcher = ENC_PATTERN.matcher(beforeDecrypt);
            if (matcher.find()) {
                try {
                    return CryptoUtils.decrypt(matcher.group(1));
                } catch (Exception e) {
                    log.error("DynamicDataSourceProperties.decrypt error ", e);
                }
            }
        }
        return beforeDecrypt;
    }

}
