package com.cqw.cblog.pojo;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Proxy(lazy = false)
@Table
@Entity(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER)
    private List<Blog> blogs=new ArrayList<>();
}
