package com.codegym.blog.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Blog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURL;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name = "blog_tags_info",
            joinColumns = @JoinColumn(name = "blogId"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tags;

    private Long views = 0l;

    // TODO : logic verify ?
//    private boolean verified;

    private boolean status = false;

}
