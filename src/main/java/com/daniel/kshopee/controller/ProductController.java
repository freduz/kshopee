package com.daniel.kshopee.controller;


import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.payload.ProductResponse;
import com.daniel.kshopee.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private final ProductService productService;
    @Autowired
    private final ObjectMapper objectMapper;



    public ProductController(ProductService productService,ObjectMapper objectMapper){
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity<ProductDto> createProduct(@RequestParam("file") List<MultipartFile> files, @RequestParam("product") String product) throws IOException {
        ProductDto productDto = objectMapper.readValue(product,ProductDto.class);
        log.info("product"+productDto.toString());

        return new ResponseEntity<ProductDto>(this.productService.add(productDto,files), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageSize",required = false,defaultValue ="20") int pageSize ,
            @RequestParam(name = "pageNo",required = false,defaultValue ="0") int pageNo,
            @RequestParam(name = "sortBy" ,defaultValue = "id" ,required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
            ){
        return new ResponseEntity<ProductResponse>(this.productService.getAllProducts(pageNo,pageSize,sortBy,sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long productId){
        return new ResponseEntity<ProductDto>(this.productService.getProductById(productId),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") long productId,@RequestBody ProductDto productDto){
        return new ResponseEntity<ProductDto>(this.productService.updateProduct(productId,productDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity deleteProductById(@PathVariable("id") long productId){
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/delete-all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
    public ResponseEntity<String> deleteAllProduct(){
        this.productService.deleteAllProduct();
        return new ResponseEntity<String>("All products deleted",HttpStatus.OK);
    }

}
