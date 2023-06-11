package com.daniel.kshopee.service;

import com.daniel.kshopee.entity.Seller;
import com.daniel.kshopee.payload.SellerDto;

public interface SellerService {
    Seller add(SellerDto sellerDto);
    Seller getProfile();
}
