package com.pgs.praktyki.rzeintern1021.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AwsS3sender {

    private final AmazonS3 amazonS3;

    @Value("${cloud.s3.endpoint.bucketname}")
    private String avatarBucketName;

    public AwsS3sender(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadToS3(MultipartFile multipartFile, Integer randomCharsCount) {
        Logger logger = LoggerFactory.getLogger(AwsS3sender.class);
        String fileName = "";
        try {
            File file = this.convertMultipartToFile(multipartFile);
            fileName = this.generateUniqueName(multipartFile, randomCharsCount);
            amazonS3.putObject(avatarBucketName, fileName, file);
            file.delete();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return this.getFileURL(fileName);
    }

    public File convertMultipartToFile(MultipartFile fileToConvert) throws IOException {
        File convertedFile = new File(fileToConvert.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(fileToConvert.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }

    public String generateUniqueName(MultipartFile multipartFile, Integer randomCharCount) {
        return RandomString.make(randomCharCount) + "_" + multipartFile.getOriginalFilename().replace(" ", "_");
    }

    public String getFileURL(final String filename) {
        return amazonS3.getUrl(avatarBucketName, filename).toString();
    }

    public void deleteFileByFilename(final String fileName) {
        amazonS3.deleteObject(avatarBucketName, fileName);
    }
}
