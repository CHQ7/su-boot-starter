package com.yunqi.starter.common.lang;


import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.common.lang.stream.StringOutputStream;
import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.common.repo.Base64;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 这些帮助函数让 Java 的某些常用功能变得更简单
 * Created by @author CHQ on 2022/1/22
 */
public abstract class Lang {

    public static int HASH_BUFF_SIZE = 16 * 1024;

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");


    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    /**
     * 将一个抛出对象的异常堆栈，显示成一个字符串
     *
     * @param e 抛出对象
     * @return 异常堆栈文本
     */
    public static String getStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        StringOutputStream sbo = new StringOutputStream(sb);
        PrintStream ps = new PrintStream(sbo);
        e.printStackTrace(ps);
        ps.flush();
        return sbo.getStringBuilder().toString();
    }

    /**
     * 生成一个未实现的运行时异常
     *
     * @return 一个未实现的运行时异常
     */
    public static RuntimeException noImplement() {
        return new RuntimeException("Not implement yet!");
    }

    /**
     * 生成一个不可能的运行时异常
     *
     * @return 一个不可能的运行时异常
     */
    public static RuntimeException impossible() {
        return new RuntimeException("r u kidding me?! It is impossible!");
    }

    /**
     * 用运行时异常包裹抛出对象，如果抛出对象本身就是运行时异常，则直接返回。
     *
     * 如果是 InvocationTargetException，那么将其剥离，只包裹其 TargetException
     * @param e 抛出对象
     * @return  运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e) {
        if (e instanceof RuntimeException)
            return (RuntimeException) e;
        if (e instanceof InvocationTargetException)
            return wrapThrow(((InvocationTargetException) e).getTargetException());
        return new RuntimeException(e);
    }

    /**
     * 根据格式化字符串，生成运行时异常
     *
     * @param format
     *            格式
     * @param args
     *            参数
     * @return 运行时异常
     */
    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }


    /**
     * 判断一个对象是否为空。它支持如下对象类型：
     * <ul>
     * <li>null : 一定为空
     * <li>数组
     * <li>集合
     * <li>Map
     * <li>其他对象 : 一定不为空
     * </ul>
     *
     * @param obj
     *            任意对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj.getClass().isArray())
            return java.lang.reflect.Array.getLength(obj) == 0;
        if (obj instanceof Collection<?>)
            return ((Collection<?>) obj).isEmpty();
        if (obj instanceof Map<?, ?>)
            return ((Map<?, ?>) obj).isEmpty();
        return false;
    }

    /**
     * 数组是否为非空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (null != array && array.length != 0);
    }

    /**
     * 获得访问者的IP地址, 反向代理过的也可以获得
     *
     * @param request 请求的req对象
     * @return 来源ip
     */
    public static String getIP(HttpServletRequest request) {
        if (request == null)
            return "";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            /*for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }*/
            ip = Arrays.stream(ips).filter(strIp -> !("unknown".equalsIgnoreCase(strIp))).findFirst().orElse(ip);
        }
        if (Strings.isBlank(ip))
            return "";
        if (isIPv4Address(ip) || isIPv6Address(ip)) {
            return ip;
        }
        return "";
    }

    /**
     * 十六进制字符串
     * <br>
     * 将十六进制字符数组转换为字符串
     * <br>
     * 十六进制（简写为hex或下标16）在数学中是一种逢16进1的进位制，一般用数字0到9和字母A到F表示（其中:AF即1015）。
     * 例如十进制数57，在二进制写作111001，在16进制写作39。
     * <br>
     * 用于: 16进制一般针对无法显示的一些二进制进行显示，常用于： 1、图片的字符串表现形式 2、加密解密 3、编码转换
     * @param hashBytes     十六进制char[]
     */
    public static String fixedHexString(byte[] hashBytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * 根据一段字符串，生成一个 Map 对象。
     *
     * @param str
     *            参照 JSON 标准的字符串，但是可以没有前后的大括号
     * @return Map 对象
     */
    public static NutMap map(String str) {
        if (null == str)
            return null;
        str = Strings.trim(str);
        if (!Strings.isEmpty(str)
                && (Strings.isQuoteBy(str, '{', '}') || Strings.isQuoteBy(str, '(', ')'))) {
            return Json.fromJson(NutMap.class, str);
        }
        return Json.fromJson(NutMap.class, "{" + str + "}");
    }

    /**
     * 将对象转换成 Map
     *
     * @param obj
     *            POJO 对象
     * @return Map 对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> obj2map(Object obj) {
        return org.nutz.lang.Lang.obj2map(obj, HashMap.class);
    }

    /**
     * 将对象转为 Nutz 的标准 Map 封装
     *
     * @param obj
     *            POJO du对象
     * @return NutMap 对象
     */
    public static NutMap obj2nutmap(Object obj) {
        return org.nutz.lang.Lang.obj2map(obj, NutMap.class);
    }


    /**
     * 将一个数组转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c
     *            分隔符
     * @param objs
     *            数组
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, T[] objs) {
        StringBuilder sb = new StringBuilder();
        if (null == objs || 0 == objs.length)
            return sb;

        sb.append(objs[0]);
        for (int i = 1; i < objs.length; i++)
            sb.append(c).append(objs[i]);

        return sb;
    }

    /**
     * 将一个数组的部分元素转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param offset
     *            开始元素的下标
     * @param len
     *            元素数量
     * @param c
     *            分隔符
     * @param objs
     *            数组
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(int offset, int len, Object c, T[] objs) {
        StringBuilder sb = new StringBuilder();
        if (null == objs || len < 0 || 0 == objs.length)
            return sb;

        if (offset < objs.length) {
            sb.append(objs[offset]);
            for (int i = 1; i < len && i + offset < objs.length; i++) {
                sb.append(c).append(objs[i + offset]);
            }
        }
        return sb;
    }

    /**
     * 将一个迭代器转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c
     *            分隔符
     * @param it
     *            集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Iterator<T> it) {
        StringBuilder sb = new StringBuilder();
        if (it == null || !it.hasNext())
            return sb;
        sb.append(it.next());
        while (it.hasNext())
            sb.append(c).append(it.next());
        return sb;
    }

    /**
     * 将一个集合转换成字符串
     * <p>
     * 每个元素之间，都会用一个给定的字符分隔
     *
     * @param c
     *            分隔符
     * @param coll
     *            集合
     * @return 拼合后的字符串
     */
    public static <T> StringBuilder concat(Object c, Collection<T> coll) {
        StringBuilder sb = new StringBuilder();
        if (null == coll || coll.isEmpty())
            return sb;
        return concat(c, coll.iterator());
    }

    /**
     * 从一个文本输入流读取所有内容，并将该流关闭
     *
     * @param reader
     *            文本输入流
     * @return 输入流所有内容
     */
    public static String readAll(Reader reader) {
        if (!(reader instanceof BufferedReader))
            reader = new BufferedReader(reader);
        try {
            StringBuilder sb = new StringBuilder();

            char[] data = new char[64];
            int len;
            while (true) {
                if ((len = reader.read(data)) == -1)
                    break;
                sb.append(data, 0, len);
            }
            return sb.toString();
        }
        catch (IOException e) {
            throw org.nutz.lang.Lang.wrapThrow(e);
        }
        finally {
            Streams.safeClose(reader);
        }
    }

    // ----------------------- 摘要加密 start -----------------------

    /**
     * 获取指定输入流的 MD5 值
     *
     * @param ins 输入流
     * @return 指定输入流的 MD5 值
     * @see #digest(String, InputStream)
     */
    public static String md5(InputStream ins) {
        return digest("MD5", ins);
    }

    /**
     * 获取指定字符串的 MD5 值
     *
     * @param cs 字符串
     * @return 指定字符串的 MD5 值
     * @see #digest(String, CharSequence)
     */
    public static String md5(CharSequence cs) {
        return digest("MD5", cs);
    }

    /**
     * 获取指定输入流的 SHA1 值
     *
     * @param ins 输入流
     * @return 指定输入流的 SHA1 值
     * @see #digest(String, InputStream)
     */
    public static String sha1(InputStream ins) {
        return digest("SHA1", ins);
    }

    /**
     * 获取指定字符串的 SHA1 值
     *
     * @param cs 字符串
     * @return 指定字符串的 SHA1 值
     * @see #digest(String, CharSequence)
     */
    public static String sha1(CharSequence cs) {
        return digest("SHA1", cs);
    }

    /**
     * 获取指定输入流的 SHA256 值
     *
     * @param ins 输入流
     * @return 指定输入流的 SHA256 值
     * @see #digest(String, InputStream)
     */
    public static String sha256(InputStream ins) {
        return digest("SHA-256", ins);
    }

    /**
     * 获取指定字符串的 SHA256 值
     *
     * @param cs 字符串
     * @return 指定字符串的 SHA256 值
     * @see #digest(String, CharSequence)
     */
    public static String sha256(CharSequence cs) {
        return digest("SHA-256", cs);
    }

    /**
     * 获取指定字符串的 SHA256 值
     *
     * @param cs 字符串
     * @return 指定字符串的 SHA256 值
     * @see #digest(String, CharSequence)
     */
    public static String sha256BySalt(CharSequence cs, CharSequence salt) {
        return sha256(sha256(cs) + sha256(salt));
    }

    /**
     * 获取指定字符串的 SHA256 值
     *
     * @param cs    字符串
     * @param salt  加密盐
     * @return      指定字符串的 SHA256 值
     */
    public static String sha256(CharSequence cs, CharSequence salt) {
        return digest("SHA-256", cs, salt, 1024);
    }

    /**
     * 从流计算出数字签名，计算完毕流会被关闭
     *
     * @param algorithm 算法，比如 "SHA1" 或者 "MD5" 等
     * @param ins       输入流
     * @return 数字签名
     */
    public static String digest(String algorithm, InputStream ins) {
        return fixedHexString(digest2(algorithm, ins));
    }

    public static byte[] digest2(String algorithm, InputStream ins) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);

            byte[] bs = new byte[HASH_BUFF_SIZE];
            int len = 0;
            while ((len = ins.read(bs)) != -1) {
                md.update(bs, 0, len);
            }

            return md.digest();
        }
        catch (NoSuchAlgorithmException e) {
            throw Lang.wrapThrow(e);
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            Streams.safeClose(ins);
        }
    }

    /**
     * 从字符串计算出数字签名
     *
     * @param algorithm     算法，比如 "SHA1" 或者 "MD5" 等
     * @param cs            字符串
     * @return              数字签名
     */
    public static String digest(String algorithm, CharSequence cs) {
        return digest(algorithm, Strings.getBytesUTF8(null == cs ? "" : cs), null, 1);
    }

    /**
     * 从字符串计算出数字签名
     *
     * @param algorithm     算法，比如 "SHA1" 或者 "MD5" 等
     * @param cs            字符串
     * @param salt          加密盐
     * @param iterations    迭代次数
     * @return              数字签名
     */
    public static String digest(String algorithm, CharSequence cs, CharSequence salt, int iterations) {
        return digest(algorithm, Strings.getBytesUTF8(null == cs ? "" : cs), null, iterations);
    }

    /**
     * 从字节数组计算出数字签名
     *
     * @param algorithm     算法，比如 "SHA1" 或者 "MD5" 等
     * @param bytes         字节数组
     * @param salt          随机字节数组
     * @param iterations    迭代次数
     * @return              数字签名
     */
    public static String digest(String algorithm, byte[] bytes, byte[] salt, int iterations) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                md.update(salt);
            }

            byte[] hashBytes = md.digest(bytes);

            for (int i = 1; i < iterations; i++) {
                md.reset();
                hashBytes = md.digest(hashBytes);
            }

            return fixedHexString(hashBytes);
        }
        catch (NoSuchAlgorithmException e) {
            throw Lang.wrapThrow(e);
        }
    }

    // ----------------------- 摘要加密 end -----------------------

    // ----------------------- 非对称加密 RSA start -----------------------

    /** 指定加密算法为RSA */
    public static final String ALGORITHM_RSA = "RSA";

    /** 密钥长度，用来初始化 512 1024 2048 4096 */
    private static final int KEY_SIZE  = 1024;

    /** ECB 加密模式 */
    private static final String ALGORITHM_RSA_ECB = "RSA/ECB/PKCS1Padding";

    /**
     * 生成密钥对
     * @return Map对象 (private=私钥, public=公钥)
     * @throws Exception 异常
     */
    public static HashMap<String, String> rsaGenerateKeyPair() throws Exception {
        // RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Key privateKey = keyPair.getPrivate();
        Key publicKey = keyPair.getPublic();

        HashMap<String, String> map = new HashMap<>(16);
        map.put("public", Base64.encode(publicKey.getEncoded()));
        map.put("private",  Base64.encode(privateKey.getEncoded()));
        return map;
    }

    /**
     * RSA公钥加密
     * @param publicKeyString 公钥(BASE64编码)
     * @param content         内容
     * @return                加密后内容
     */
    public static String rsaEncryptByPublic(String publicKeyString, String content) {
        try {
            // 获得公钥对象
            PublicKey publicKey =  publicKey(publicKeyString);
            // 加密数据长度 <= 模长-11,如果明文长度大于模长-11则要分组加密
            int keyLen = ((RSAPublicKey) publicKey).getModulus().bitLength()  / 8 -11; // 模长
            return Base64.encode(decipher(Cipher.ENCRYPT_MODE, publicKey ,content.getBytes(StandardCharsets.UTF_8) , keyLen));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA私钥加密
     * @param privateKeyString  私钥(BASE64编码)
     * @param content           内容
     * @return                  加密后内容
     */
    public static String rsaEncryptByPrivate(String privateKeyString, String content) {
        try {
            PrivateKey privateKey = privateKey(privateKeyString);
            // 加密数据长度 <= 模长-11,如果明文长度大于模长-11则要分组加密
            int splitLength = (((RSAPrivateKey) privateKey).getModulus().bitLength()  / 8) - 11;
            return Base64.encode(decipher(Cipher.ENCRYPT_MODE, privateKey, content.getBytes(StandardCharsets.UTF_8), splitLength));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA公钥解密
     * @param publicKeyString   公钥(BASE64编码)
     * @param content           已加密内容
     * @return                  解密后内容
     */
    public static String rsaDecryptByPublic(String publicKeyString, String content) {
        try {
            PublicKey publicKey =  publicKey(publicKeyString);
            // 该密钥能够加密的最大字节长度
            int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength()  / 8;
            return new String(decipher(Cipher.DECRYPT_MODE, publicKey, Base64.decode(content), splitLength));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * RSA私钥解密
     * @param privateKeyString  公钥(BASE64编码)
     * @param content           已加密内容
     * @return                  解密后内容
     */
    public static String rsaDecryptByPrivate(String privateKeyString, String content) {
        try {
            PrivateKey privateKey = privateKey(privateKeyString);
            // 该密钥能够加密的最大字节长度
            int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8;
            return new String(decipher(Cipher.DECRYPT_MODE, privateKey, Base64.decode(content), splitLength));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // ---------- 获取*钥

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr      公钥字符串
     * @return                  公钥
     * @throws Exception        Exception
     */
    public static PublicKey publicKey(String publicKeyStr) throws Exception {
        try {
            String publicKey = publicKeyStr
                    .replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s+", "");

            byte[] buffer = Base64.decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Objects.requireNonNull(buffer));
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No such algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Illegal private key");
        } catch (NullPointerException e) {
            throw new Exception("The private key data is empty");
        }
    }

    /**
     * 从字符串中加载私钥
     * @param privateKeyStr     私钥字符串
     * @return                  私钥
     * @throws Exception        Exception
     */
    public static PrivateKey privateKey(String privateKeyStr) throws Exception {
        try {
            String privateKey = privateKeyStr
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] buffer = Base64.decode(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(buffer));
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("No such algorithm");
        } catch (InvalidKeySpecException e) {
            throw new Exception("Illegal private key");
        } catch (NullPointerException e) {
            throw new Exception("The private key data is empty");
        }

    }

    // ---------- 一些辅助方法


    /**
     * 数据加密解密
     * @param mode      Cipher.ENCRYPT_MODE:加密、Cipher.DECRYPT_MODE:解密
     * @param key       PublicKey:公钥   PrivateKey:私钥
     * @param data      加密或解密的字节数组
     * @param keyLen    模唱
     * @return          加密或解密的字节数组
     */
    private static byte[] decipher(int mode, Key key, byte[] data,int keyLen) {
        try {
            // 数据加密解密
            Cipher cipher = Cipher.getInstance(ALGORITHM_RSA_ECB);
            cipher.init(mode, key);

            // -------------------------------------------------
            // 分段解密
            // -------------------------------------------------
            byte[] enBytes = null;
            for (int i = 0; i < data.length; i += keyLen) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(Array.subarray(data, i,i + keyLen));
                enBytes = Array.addAll(enBytes, doFinal);
            }
            return enBytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    // ----------------------- 非对称加密 RSA end -----------------------

    // ----------------------- 签名和验证 start -----------------------

    /**
     * 用私钥对信息生成数字签名
     *
     * @param privateKeyStr 私钥字符串(BASE64编码)
     * @param data          加密字符串
     * @param signAlg       [SHA256withRSA / SHA1withDSA / MD2withRSA /MD5withRSA /SHA1withRSA]
     * @return              数字签名
     */
    public static String sign(String privateKeyStr, String data, String signAlg) throws Exception {
        return sign(privateKey(privateKeyStr),data, signAlg);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param privateKey    私钥
     * @param data          加密字符串
     * @param signAlg       [SHA256withRSA / SHA1withDSA / MD2withRSA /MD5withRSA /SHA1withRSA]
     * @return              数字签名
     */
    public static String sign(PrivateKey privateKey, String data, String signAlg){
        try {
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(signAlg);
            signature.initSign(privateKey);
            signature.update(data.getBytes(Encoding.CHARSET_UTF8));
            return Base64.encode(signature.sign());
        } catch (Exception  e) {
            throw new RuntimeException("生成数字签名异常");
        }
    }

    /**
     * 校验数字签名
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @param data      验签加密内容
     * @param signAlg   [SHA256withRSA / SHA1withDSA / MD2withRSA /MD5withRSA /SHA1withRSA]
     * @return Boolean  校验成功返回true 失败返回false
     */
    public static Boolean verifySign(String publicKey, String sign, String data, String signAlg) throws Exception {
        return verifySign(publicKey(publicKey), sign, data, signAlg);
    }

    /**
     * 校验数字签名
     *
     * @param publicKey     公钥
     * @param sign          数字签名
     * @param data          验签加密内容
     * @param signAlg       [SHA256withRSA / SHA1withDSA / MD2withRSA /MD5withRSA /SHA1withRSA]
     * @return              Boolean
     */
    public static Boolean verifySign(PublicKey publicKey, String sign, String data, String signAlg){
        try {
            Signature signature = Signature.getInstance(signAlg);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(Encoding.CHARSET_UTF8));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("校验数字签名异常");
        }
    }

    // ----------------------- 签名和验证 end -----------------------

    // ----------------------- 获取证书 start -----------------------

    /**
     * 获取证书序列号
     *
     * @param CertPath  证书路径
     * @return          证书序列号
     */
    public static String getCertSerialNo(String CertPath) {
        X509Certificate certificate = getCertificate(org.nutz.lang.Streams.fileIn(CertPath));
        return certificate.getSerialNumber().toString(16).toUpperCase();
    }

    /**
     * 获取证书
     *
     * @param inputStream   输入流
     * @return              证书信息
     */
    public static X509Certificate getCertificate(InputStream inputStream) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException var3) {
            throw new RuntimeException("证书已过期", var3);
        } catch (CertificateNotYetValidException var4) {
            throw new RuntimeException("证书尚未生效", var4);
        } catch (CertificateException var5) {
            throw new RuntimeException("无效的证书", var5);
        }
    }

    // ----------------------- 获取证书 end -----------------------
}
