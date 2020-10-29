package com.cqw.cblog.pojo;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity(name="t_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    @ManyToOne
    private Comment parentComment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    @ManyToOne
    private Blog blog;
    private boolean adminComment;
    @OneToMany(mappedBy = "parentComment",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Comment>  replyComments=new ArrayList<>();
}
