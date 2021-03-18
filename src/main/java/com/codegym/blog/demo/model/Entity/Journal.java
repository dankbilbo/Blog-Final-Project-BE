package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Journal{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String title;
    private LocalDateTime createdAt;

    @Enumerated
    private JournalStatus status;

    private String password;
}
