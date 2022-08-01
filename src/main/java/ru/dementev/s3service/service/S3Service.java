package ru.dementev.s3service.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface S3Service {
    String putObject(String bucketName, String key, InputStream inputStream);

    ByteBuffer getObject(String bucketName, String key) throws IOException;

}
