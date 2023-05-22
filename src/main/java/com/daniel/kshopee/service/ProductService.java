package com.daniel.kshopee.service;


import com.daniel.kshopee.payload.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto add(ProductDto product);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(long id);
    ProductDto updateProduct(long id,ProductDto productDto);

    void deleteProduct(long id);
}
