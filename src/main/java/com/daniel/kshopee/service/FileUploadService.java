package com.daniel.kshopee.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {

    List<String> uploadFile(List<MultipartFile> file) throws IOException;
}
