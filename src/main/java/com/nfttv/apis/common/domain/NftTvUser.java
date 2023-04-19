package com.nfttv.apis.common.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NftTvUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(length = 100, nullable = false)
    public String username;

    @Column(length = 50, nullable = false)
    public String email;

    @Column(length = 50, nullable = false)
    public String firstname;

    @Column(length = 50, nullable = false)
    public String lastname;

}
