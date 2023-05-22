package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Product;
import com.daniel.kshopee.exception.ResourceNotFoundException;
import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.repository.ProductRepository;
import com.daniel.kshopee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    private Product mapToEntity(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .quantity(productDto.getQuantity())
                .build();
    }

    private ProductDto mapToDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public ProductDto add(ProductDto productDto) {
        Product product = this.mapToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return this.mapToDto(savedProduct);

    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        Function<Product,ProductDto> mapProduct = this::mapToDto;
        return products.stream().map(mapProduct).collect(Collectors.toList());
    }



    @Override
    public ProductDto getProductById(long id) {
       Product product = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","ID",id));
       return this.mapToDto(product);
    }

    @Override
    public ProductDto updateProduct(long id,ProductDto productDto){
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","ID",id));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        return this.mapToDto(this.productRepository.save(product));

    }

    @Override
    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }
}
