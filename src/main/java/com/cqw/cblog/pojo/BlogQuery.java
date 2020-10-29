package com.cqw.cblog.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {
    private String title;
    private Long type;
    private boolean recommend;
}
