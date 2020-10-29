package com.cqw.cblog.Service;

import com.cqw.cblog.pojo.Blog;
import com.cqw.cblog.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query,Pageable pageable);
    List<Blog> listTopBlog(Integer size);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(Long tagId, Pageable pageable);
    Map<String,List<Blog>> archiveBlog();
    Long countBlog();
    List<Blog> listRecommendBlogTop(Integer i);
}
