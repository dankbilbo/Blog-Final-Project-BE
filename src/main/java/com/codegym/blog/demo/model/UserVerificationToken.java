package com.codegym.blog.demo.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserVerificationToken extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime verifiedAt;
    private LocalDateTime expiredAt;

    @ManyToOne
    private User user;

}
