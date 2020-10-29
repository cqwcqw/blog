package com.cqw.cblog.Controller;

import com.cqw.cblog.NotFoundException;
import com.cqw.cblog.Service.BlogService;
import com.cqw.cblog.Service.TagService;
import com.cqw.cblog.Service.TypeService;
import com.cqw.cblog.pojo.Blog;
import com.cqw.cblog.pojo.BlogQuery;
import com.cqw.cblog.pojo.Type;
import com.cqw.cblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/blogs")
    public String blogs(Model model, @PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typeService.listTypes());
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(Model model, @PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }
    @GetMapping("blogs/add")
    public String add(Model model){
        Blog b= new Blog();
        b.setId(null);
        b.setType(new Type());
        model.addAttribute("blog",b);
        setTypeAndTag(model);
        return "admin/addblog";
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listTypes());
        model.addAttribute("tags",tagService.listTags());
    }
    @GetMapping("blogs/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog b=blogService.getBlog(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        b.init();
        model.addAttribute("blog",b);
        return "admin/addblog";
    }
    @PostMapping("/blogs/add")
    public String add(HttpSession session, Blog blog, RedirectAttributes attributes){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.matchTag(blog.getTagsId()));
        Blog b;
        b=blogService.saveBlog(blog);
        if (b!=null){
            attributes.addFlashAttribute("msg","新增成功");
        }else{
            attributes.addFlashAttribute("msg","新增失败");
        }
        return "redirect:/admin/blogs";
    }
    @PostMapping("/blogs/edit/{id}")
    public String edit(HttpSession session, Blog blog, RedirectAttributes attributes,@PathVariable Long id){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.matchTag(blog.getTagsId()));
        Blog b;
        b=blogService.updateBlog(id,blog);
        if (b!=null){
            attributes.addFlashAttribute("msg","修改成功");
        }else{
            attributes.addFlashAttribute("msg","修改失败");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }
}
