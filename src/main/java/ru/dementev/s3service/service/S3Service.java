package ru.dementev.s3service.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public interface S3Service {
    void putObject(String bucketName, String key, InputStream inputStream, long size);

    ByteBuffer getObject(String bucketName, String key) throws IOException;

    URL getPreSignedUrl(String bucketName, String key);

}
