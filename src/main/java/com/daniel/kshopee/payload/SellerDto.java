package com.daniel.kshopee.payload;


import com.daniel.kshopee.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SellerDto {

    private long id;
    private String fullName;
    private String accountNo;
    private String phoneNo;
    private String address;

    private User user;


}
