package com.dianmic.dmutil.service;

import java.io.InputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.dianmic.dmutil.util.CommonUtil;
import com.dianmic.dmutil.util.Constant;
import com.dianmic.dmutil.util.DateUtil;
import com.dianmic.dmutil.util.StringUtil;

/**
 * 
 * 
 * @date 2018年6月19日
 * 
 * @author swf
 *
 * @Description 阿里云上传文件service
 *
 */
@Service("ossService")
public class OssService {

    Logger                   log             = Logger.getLogger(OssService.class);

    private static String    endpoint        = Constant.debug ?

            "http://oss-test.dianmic.com/"

            // : "http://oss.dianmic.com/";
            : "http://oss-cn-shenzhen-internal.aliyuncs.com/";

    private static String    accessKeyId     = "LPzcR6TvQm5xNtA4";
    private static String    accessKeySecret = "RlHxiLvuVs75yhj5fJM6J4DHV8xVqV";

    private static OSSClient client          = null;

    private static String    bucketName      = Constant.debug ? "dmtest" : "dianmic";

    public OssService() {
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 
     * @date 2016年9月13日 下午1:45:19
     * 
     * @author laikunzhen
     * 
     * @Description 上传文件
     * 
     * @param fileName
     *            完整文件名，文件夹目录使用文件分隔符隔开"/"
     * @param inputStream
     * @param objectMetadata
     * @throws Throwable
     */
    public String uploadFile(String folder, InputStream inputStream, ObjectMetadata objectMetadata, String suffix)
            throws Throwable {
        if (StringUtil.isEmpty(folder)) {
            folder = Constant.dir_user_img;
        }
        String yyMMdd = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
        String fileName = CommonUtil.getCurrrentTime4FileName();
        fileName = yyMMdd + "/" + fileName + suffix;

        if (!folder.endsWith(Constant.split_xie)) {
            folder += Constant.split_xie;
        }
        String uploadFolder = folder + fileName;
        PutObjectResult putObjectResult = client.putObject(bucketName, uploadFolder, inputStream, objectMetadata);
        if (putObjectResult != null) {
            log.info(String.format("[oss-上传成功], uploadFolder=[%s], etag=[%s].", uploadFolder,
                    putObjectResult.getETag()));
        } else {
            fileName = "";
            log.error(String.format("[oss-上传失败], uploadFolder=[%s].", uploadFolder));
        }
        return fileName;
    }

    /**
     * 
     * @date 2018年1月15日 下午1:48:22
     * 
     * @author swf
     * 
     * @Description
     * 
     * @param folder
     * @param selfFileName
     *            自带文件名（含文件后缀名）
     * 
     * @param inputStream
     * @param objectMetadata
     * @return
     * @throws Throwable
     */
    public String uploadFile(String folder, String selfFileName, InputStream inputStream, ObjectMetadata objectMetadata)
            throws Throwable {
        String yyMMdd = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
        String fileName = yyMMdd + "/" + selfFileName;

        if (!folder.endsWith(Constant.split_xie)) {
            folder += Constant.split_xie;
        }
        String uploadFolder = folder + fileName;
        PutObjectResult putObjectResult = client.putObject(bucketName, uploadFolder, inputStream, objectMetadata);
        if (putObjectResult != null) {
            log.info(String.format("[oss-上传成功], uploadFolder=[%s], etag=[%s].", uploadFolder,
                    putObjectResult.getETag()));
        } else {
            log.error(String.format("[oss-上传失败], uploadFolder=[%s].", uploadFolder));
        }
        return fileName;
    }

    /**
     * 
     * @date 2016年9月13日 下午1:50:44
     * 
     * @author laikunzhen
     * 
     * @Description 判断文件是否存在
     * 
     * @param fileName
     *            完整文件名,文件夹及文件名称
     * @return
     */
    public boolean fileIsExist(String fileName) {
        return client.doesObjectExist(bucketName, fileName);
    }

    /**
     * 
     * @date 2016年9月13日 下午3:05:26
     * 
     * @author laikunzhen
     * 
     * @Description 获取文件列表
     * 
     * @param folder
     *            文件夹(多层文件夹使用文件分隔符隔开"/")
     * @return
     */
    public ObjectListing fileList(String folder) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(folder);
        return client.listObjects(listObjectsRequest);
    }

    public String saveFile(MultipartFile file, String folder) {
        String fileName = "";
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            String suffix = CommonUtil.getFileSuffix(file.getOriginalFilename());
            fileName = uploadFile(folder, file.getInputStream(), objectMetadata, suffix);
        } catch (Throwable e) {
        }
        return fileName;
    }

    public String saveFileWithFileName(MultipartFile file, String folder, String fileName) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            // String suffix =
            // CommonUtil.getFileSuffix(file.getOriginalFilename());
            fileName = uploadFile(folder, fileName, file.getInputStream(), objectMetadata);
        } catch (Throwable e) {
        }
        return fileName;
    }

}
