package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Product;
import com.daniel.kshopee.entity.ProductMedia;
import com.daniel.kshopee.entity.Seller;
import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.exception.ResourceNotFoundException;
import com.daniel.kshopee.payload.ProductDto;
import com.daniel.kshopee.payload.ProductResponse;
import com.daniel.kshopee.repository.ProductMediaRepository;
import com.daniel.kshopee.repository.ProductRepository;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.service.FileUploadService;
import com.daniel.kshopee.service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final FileUploadService fileUploadService;
    @Autowired
    private final ProductMediaRepository productMediaRepository;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository,
                              FileUploadService fileUploadService,
                              ProductMediaRepository productMediaRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
        this.productMediaRepository = productMediaRepository;

    }

    private Product mapToEntity(ProductDto productDto){

        String  email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).get();

        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .quantity(productDto.getQuantity())
                .user(user)
                .build();
    }

    private ProductDto mapToDto(Product product){

        Seller seller = product.getUser().getSeller();
        System.out.println(seller.getAccountNo());
        List<ProductMedia> medias = product.getProductMedia();
        List<String> images = new ArrayList<>();
        if(medias != null) {
            images = medias.stream().map(media -> media.getUrl()).collect(Collectors.toList());
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .sellerName(seller.getFullName())
                .images(images)
                .build();
    }





    @Override
    @Transactional
    public ProductDto add(ProductDto productDto, List<MultipartFile> files) throws IOException {
        List<String> urls = fileUploadService.uploadFile(files);

        Product product = mapToEntity(productDto);
        Product savedProduct = productRepository.save(product);

        List<ProductMedia> medias = new ArrayList<>();
        for (String url : urls) {
            ProductMedia productMedia =ProductMedia.builder()
                    .url(url)
                    .product(savedProduct)
                    .build();
            medias.add(productMedia);
        }
       List<ProductMedia> mediaList =  productMediaRepository.saveAll(medias);
        savedProduct.setProductMedia(mediaList);

        return mapToDto(savedProduct);
    }
    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Product> products = this.productRepository.findAll(pageable);
        Function<Product,ProductDto> mapProduct = this::mapToDto;
        List<ProductDto> content = products.getContent().stream().map(mapProduct).collect(Collectors.toList());
        ProductResponse productResponse = ProductResponse.builder()
                .content(content)
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .isLast(products.isLast())
                .build();

        System.out.println(productResponse.toString());
        return productResponse;
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
