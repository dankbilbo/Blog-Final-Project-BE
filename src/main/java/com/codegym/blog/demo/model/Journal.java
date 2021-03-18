package com.codegym.blog.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Journal extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String title;

    @Enumerated
    private JournalStatus status;

    private String password;
}
