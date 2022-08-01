package ru.dementev.s3service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.dementev.s3service.service.S3Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class S3ServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(S3ServiceApplication.class, args);
    }

}
