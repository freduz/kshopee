package com.daniel.kshopee.service;


import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.payload.ProductResponse;

public interface ProductService {
    ProductDto add(ProductDto product);
    ProductResponse getAllProducts(int pageNo, int pageSize,String sortBy,String sortDir);
    ProductDto getProductById(long id);
    ProductDto updateProduct(long id,ProductDto productDto);

    void deleteProduct(long id);
}
