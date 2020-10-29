package com.cqw.cblog.Controller;

import com.cqw.cblog.NotFoundException;
import com.cqw.cblog.Service.BlogService;
import com.cqw.cblog.Service.TagService;
import com.cqw.cblog.Service.TypeService;
import com.cqw.cblog.pojo.Blog;
import javassist.compiler.NoFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypesTop(7));
        model.addAttribute("tags", tagService.listTagsTop(10));
        model.addAttribute("recommendBlogs", blogService.listTopBlog(8));
        return "index";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog=blogService.getAndConvert(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "detail";
    }
}
