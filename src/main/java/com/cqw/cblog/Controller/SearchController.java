package com.cqw.cblog.Controller;

import com.cqw.cblog.Service.BlogService;
import com.cqw.cblog.pojo.BlogQuery;
import com.cqw.cblog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/search/{str}")
    public String searchTitle(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable String str, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+str+"%", pageable));
        model.addAttribute("query", str);
        return "search";
    }
    @PostMapping("/search")
    public String searchPage(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,@RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
}
