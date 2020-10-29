package com.cqw.cblog.Controller;

import com.cqw.cblog.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/footer/latestBlog")
    public String latestBlog(Model model) {
        model.addAttribute("latestBlogList", blogService.listRecommendBlogTop(3));
        return "common :: latestBlogList";
    }
}
