package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURL;
    private LocalDateTime createdAt;

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

    public Blog(String title, String content, String shortDescription, String previewImageURL, LocalDateTime createdAt, Category category, User user, List<Tag> tags, Long views, boolean status) {
        this.title = title;
        this.content = content;
        this.shortDescription = shortDescription;
        this.previewImageURL = previewImageURL;
        this.createdAt = createdAt;
        this.category = category;
        this.user = user;
        this.tags = tags;
        this.views = views;
        this.status = status;
    }
}
