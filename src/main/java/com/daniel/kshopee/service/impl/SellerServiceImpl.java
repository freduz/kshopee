package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Seller;
import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.payload.SellerDto;
import com.daniel.kshopee.repository.SellerRepository;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.service.SellerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;
    private UserRepository userRepository;

    public SellerServiceImpl(SellerRepository sellerRepository,UserRepository userRepository){
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;

    }
    @Override
    public Seller add(SellerDto sellerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        Seller seller = sellerRepository.save(mapToEntity(sellerDto));
        user.setSeller(seller);
        userRepository.save(user);
        return seller;
    }

    @Override
    public Seller getProfile() {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
       User user = userRepository.findByEmail(authentication.getName()).get();
       return user.getSeller();
    }


    private Seller mapToEntity(SellerDto sellerDto){
        return Seller.builder()
                .fullName(sellerDto.getFullName())
                .accountNo(sellerDto.getAccountNo())
                .address(sellerDto.getAddress())
                .phoneNo(sellerDto.getPhoneNo())
                .build();
    }
}
