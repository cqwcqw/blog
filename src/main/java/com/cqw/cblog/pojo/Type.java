package com.cqw.cblog.pojo;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Proxy(lazy = false)
@Table
@Entity(name="t_type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "分类标题不能为空")
    private String name;
    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<>();
}
