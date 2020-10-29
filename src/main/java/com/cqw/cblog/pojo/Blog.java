package com.cqw.cblog.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Proxy(lazy = false)
@Table
@Entity(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String firstPicture;
    private String flag;
    private Integer view;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
//    relation
    @ManyToOne
    private Type type;
    @Transient
    private String tagsId;
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Tag> tags= new ArrayList<>();
    @ManyToOne
    private User user;
    private String description;
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "blog",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Comment> comments=new ArrayList<>();
    public void init(){
        this.tagsId=tagToId(this.getTags());
    }
    private String tagToId(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for(Tag t:tags){
             if (flag){
                 ids.append(",");
             }else {
                 flag=true;
             }
             ids.append(t.getId());
            }
            return ids.toString();
        }else {
            return tagsId;
        }
    }
}
