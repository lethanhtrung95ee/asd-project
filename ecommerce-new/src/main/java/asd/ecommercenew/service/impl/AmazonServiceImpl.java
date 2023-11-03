package asd.ecommercenew.service.impl;

import asd.ecommercenew.service.AmazonService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonServiceImpl implements AmazonService {

    private AmazonS3 amazonS3;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.amazonS3 = new AmazonS3Client(credentials);
//        this.amazonS3.setRegion(new Region(Region.EU_CENTRAL_1));

        amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1) // Set the desired AWS region here
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey, this.secretKey)))
                .build();
    }

    @Override
    public String uploadFile(String base64) {

        String fileUrl = "";
        try {
            byte[] fileBytes = Base64.getDecoder().decode(base64);
            String fileName = generateFileName();
            uploadFileTos3bucket(fileName, fileBytes);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    @Override
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }

    private String generateFileName() {
        return new Date().getTime() + "-" + UUID.randomUUID() + ".png";
    }

    private void uploadFileTos3bucket(String fileName, byte[] fileBytes) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(fileBytes), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}