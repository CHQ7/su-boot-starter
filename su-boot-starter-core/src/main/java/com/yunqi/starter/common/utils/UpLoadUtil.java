package com.yunqi.starter.common.utils;

import com.yunqi.starter.common.constant.GlobalConstant;
import com.yunqi.starter.common.lang.Files;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.random.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * 文件上传工具类
 * Created by @author CHQ on 2022/10/27
 */
@Slf4j
public class UpLoadUtil {

    /**
     * 上传文件
     * @param file  上传文件的信息
     * @return      上传路径
     */
    public static String upLoadFile(MultipartFile file) {
        return upLoad(file,"/file");
    }

    /**
     * 上传图片
     * @param file  上传文件的信息
     * @return      上传路径
     */
    public static String upLoadImage(MultipartFile file) {
        return upLoad(file,"/image");
    }

    /**
     * 本地文件存储
     * @param file    上传文件的信息
     * @param path    上传文件夹目录
     * @return        上传路径
     */
    public static String upLoad(MultipartFile file, String path) {
        try {
            if (file == null) {
                return "";
            }

            // 获取后缀名
            String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf("."));
            // 日期路径
            String dateStr = DateUtil.format(DateUtil.date(), "yyyyMMdd");
            // 文件名称
            String fileName = R.UU32() + suffix;

            // 拼接创建文件地址
            String f = path +  "/" + dateStr + "/" + fileName;

            // 文件路径 = 上传路径 + 写入路径
            String filePath = GlobalConstant.DEFAULT_SYSTEM_UPLOAD_PATH + path +  "/" + dateStr + "/" + fileName;

            // 写入文件
            Files.write(new File( GlobalConstant.DEFAULT_SYSTEM_ROOT + filePath), file.getInputStream());
            // 返回文件路径
            return filePath;
        } catch (Throwable e) {
            log.info("IO Upload Files FAIL");
            return "";
        }
    }

}
