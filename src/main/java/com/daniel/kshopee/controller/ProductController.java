package com.daniel.kshopee.controller;


import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.payload.ProductResponse;
import com.daniel.kshopee.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private final ProductService productService;



    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product){
        log.info("product"+product.toString());
        return new ResponseEntity<ProductDto>(this.productService.add(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageSize",required = false,defaultValue ="10") int pageSize ,
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
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") long productId,@RequestBody ProductDto productDto){
        return new ResponseEntity<ProductDto>(this.productService.updateProduct(productId,productDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") long productId){
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
