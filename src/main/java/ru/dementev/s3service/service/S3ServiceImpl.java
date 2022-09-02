package ru.dementev.s3service.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {


    private final AmazonS3 amazonS3;

    @Override
    public void putObject(String bucketName, String key, InputStream inputStream, long size) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        amazonS3.putObject(putObjectRequest);
    }

    @Override
    public ByteBuffer getObject(String bucketName, String key) throws IOException {
        S3Object object = amazonS3.getObject(bucketName, key);
        try (S3ObjectInputStream inputStream = object.getObjectContent()) {
            return ByteBuffer.wrap(inputStream.readAllBytes());
        }
    }

    @Override
    public URL getPreSignedUrl(String bucketName, String key) {
        return amazonS3.generatePresignedUrl(bucketName, key, new DateTime().plusMinutes(5).toDate());
    }
}
