package com.daniel.kshopee.controller;


import com.daniel.kshopee.entity.Seller;
import com.daniel.kshopee.payload.SellerDto;
import com.daniel.kshopee.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

  private SellerService sellerService;

   public SellerController(SellerService sellerService){
       this.sellerService = sellerService;
   }


    @PostMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Seller> addProfile(@RequestBody(required = false) SellerDto sellerDto){
        Seller seller = sellerService.add(sellerDto);
        return new ResponseEntity(seller,HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Seller> getProfileData(){
       Seller seller =  this.sellerService.getProfile();
        System.out.println(seller.getAccountNo());
        return new ResponseEntity<Seller>(seller,HttpStatus.CREATED);
    }
}
