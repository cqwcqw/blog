package com.cqw.cblog.Controller;

import com.cqw.cblog.Service.BlogService;
import com.cqw.cblog.Service.CommentService;
import com.cqw.cblog.pojo.Comment;
import com.cqw.cblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "detail :: commentList";
    }
    @PostMapping("/comments")
    public String add(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar+new Random().nextInt(6)+".jpg");
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
    @GetMapping("/comments/delete/{blogId}/{commentId}")
    public String deleteComments(@PathVariable Long blogId, Model model,@PathVariable Long commentId) {
        model.addAttribute("blog", blogService.getAndConvert(blogId));
        commentService.deleteComment(commentId);
        return "detail";
    }
}
