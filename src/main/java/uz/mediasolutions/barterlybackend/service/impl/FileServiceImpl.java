package uz.mediasolutions.barterlybackend.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mediasolutions.barterlybackend.exceptions.RestException;
import uz.mediasolutions.barterlybackend.service.abs.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value(("${application.bucket.name}"))
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            File converted = convertMultipartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, converted));
            converted.deleteOnExit();
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60 * 24 * 7; // 7 kun
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileName)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return ResponseEntity.ok(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw RestException.restThrow("Cannot upload file " + file.getOriginalFilename(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadFile(String fileName) {
        try {
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, fileName));
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(bytes);

            return ResponseEntity
                    .ok()
                    .contentLength(bytes.length)
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw RestException.restThrow("Cannot download file " + fileName, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteFile(String url) {
        String fileName = extractFilenameFromUrl(url);
        try {
            if (fileName != null) {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
                return ResponseEntity.ok("File deleted: " + fileName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(e.getStatusCode()).body("Error deleting file: " + fileName);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            System.out.println("File could not be written");
        }
        return file;
    }

    public static String extractFilenameFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            return Paths.get(path).getFileName().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
//            throw new RuntimeException("Failed to extract filename from URL", e);
        }
    }

    public boolean exists(AmazonS3 s3Client, String bucketName, String fileName) {
        return s3Client.doesObjectExist(bucketName, fileName);
    }
}
