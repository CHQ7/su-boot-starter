package com.yunqi.starter.common.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 编码
 * Created by @author JsckChin on 2022/1/27
 */
public final class Encoding {

    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";
    public static final String ASCII = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF16BE = "UTF-16BE";
    public static final String UTF16LE = "UTF-16LE";
    public static final String UTF16 = "UTF-16";

    public static final Charset CHARSET_UTF8 = Charset.forName(UTF8);
    public static final Charset CHARSET_GBK = Charset.forName(GBK);
    public static final Charset CHARSET_GB2312 = Charset.forName(GB2312);
    public static final Charset CHARSET_ASCII = Charset.forName(ASCII);
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
    public static final Charset CHARSET_UTF16 = Charset.forName(UTF16);
    public static final Charset CHARSET_UTF16BE = Charset.forName(UTF16BE);
    public static final Charset CHARSET_UTF16LE = Charset.forName(UTF16LE);

    /**
     * 默认编码
     * @return 默认编码
     */
    public static String defaultEncoding() {
        return Charset.defaultCharset().name();
    }

    /**
     * 编码URI组件
     * @param str  要翻译的String
     * @return     翻译后的String
     */
    public static String encodeURIComponent(String str) {
        try {
            return URLEncoder.encode(str, UTF8);
        }
        catch (UnsupportedEncodingException e) {
            throw Lang.wrapThrow(e);
        }
    }

    /**
     * 解码URI组件
     * @param str   要解码的String
     * @return      新解码的String
     */
    public static String decodeURIComponent(String str) {
        try {
            return URLDecoder.decode(str, UTF8);
        }
        catch (UnsupportedEncodingException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
