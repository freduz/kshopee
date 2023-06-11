package com.daniel.kshopee.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.daniel.kshopee.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private Cloudinary cloudinary;

    public FileUploadServiceImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }
    @Override
    public List<String> uploadFile(List<MultipartFile> files) throws IOException {

        Function<MultipartFile,String> upload = (image) -> {
            try {
                Map<?,?> result = (Map<?, ?>) this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("folder","products"));
                return (String) result.get("secure_url");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        List<String> urls = files.stream().map(upload).collect(Collectors.toList());

        return  urls;


//       Map<?,?> result =  this.cloudinary.uploader().upload(file.getBytes(),
//               ObjectUtils.asMap("folder","products")
//        );
//        return (String) result.get("secure_url");
    }
}
