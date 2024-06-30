package com.example.webCommon.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class OssUtils {
    private static final String bucketName = "cqyyds";                      // 阿里云的容器名
    private static final String transferProtocol = "https://";              // 超文本协议
    private static final String address = "oss-cn-hangzhou.aliyuncs.com";   // 华东地区地址

    // 外链的前缀
    private static final String prefix = transferProtocol+bucketName+"."+address+"/";
    // 图片类型匹配
    private static final Set<String> IMAGE_SUFFIX=Set.of("jpg","png");
    // 视频类型
    private static final Set<String> VIDEO_SUFFIX=Set.of("mp4", "mov", "avi");

    private OssUtils() {
    }

    public static String pushVideo(MultipartFile video) {
        String fileName=video.getOriginalFilename();
        AssertUtils.isTrue(isVideo(fileName), "上传的文件不是视频");
        return push(video, fileName);
    }



    public static String pushImage(MultipartFile image){
        String fileName=image.getOriginalFilename();
        AssertUtils.isTrue(isImage(fileName), "上传的文件不是图片");
        return push(image, fileName);
    }

    private static String push(MultipartFile file, String fileName) {
        //华东地区的ip地址，
        String endpoint = transferProtocol+address;
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = null;
        try {
            credentialsProvider = CredentialsProviderFactory
                    .newEnvironmentVariableCredentialsProvider();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            throw new RuntimeException(e);
        }

        String imageName = joinImageName(fileName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            ossClient.putObject(bucketName, imageName, file.getInputStream());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return prefix + imageName;
    }



    public static boolean isImage(String fileName) {
        return verifyFile(fileName, IMAGE_SUFFIX);
    }

    public static boolean isVideo(String fileName) {
        return verifyFile(fileName, VIDEO_SUFFIX);
    }

    public static boolean verifyFile(String fileName, Set<String> suffixSet) {
        if(Strings.isEmpty(fileName)) {
            return false;
        }
        int pointIndex = fileName.lastIndexOf(".");
        String ext = fileName.substring(pointIndex + 1);
        return suffixSet.contains(ext);
    }


    private static String joinImageName(String fileName) {
        LocalDate currentDate = LocalDate.now();

        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        UUID uuid = UUID.randomUUID();
        return year+"/"+month+"/"+day+"/"+uuid+"_"+fileName;
    }
}
