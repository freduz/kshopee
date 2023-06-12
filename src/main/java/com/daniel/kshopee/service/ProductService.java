package com.daniel.kshopee.service;


import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto add(ProductDto product, List<MultipartFile> file) throws IOException;
    ProductResponse getAllProducts(int pageNo, int pageSize,String sortBy,String sortDir);
    ProductDto getProductById(long id);
    ProductDto updateProduct(long id,ProductDto productDto);

    void deleteProduct(long id);
    void deleteAllProduct();
}
