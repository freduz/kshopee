package com.daniel.kshopee.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sellers_profile_tbl")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;
    private String accountNo;
    private String phoneNo;
    private String address;

    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private User user;
}
