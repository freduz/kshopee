package com.daniel.kshopee.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ProductResponse {
    private List<ProductDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}
