package asd.ecommercenew.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public interface AmazonService {
    String uploadFile(String base64);

    String deleteFileFromS3Bucket(String fileUrl);
}
