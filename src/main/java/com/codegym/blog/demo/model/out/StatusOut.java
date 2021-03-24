package com.codegym.blog.demo.model.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusOut {
    private Long id;
    private boolean isLiked;
    private Long userId;
    private Long blogId;

}
