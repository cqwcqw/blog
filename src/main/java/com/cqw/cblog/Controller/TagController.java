package com.cqw.cblog.Controller;

import com.cqw.cblog.NotFoundException;
import com.cqw.cblog.Service.TagService;
import com.cqw.cblog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/add")
    public String add(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/addtag";
    }
    @GetMapping("/tags/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Tag tag=tagService.getTag(id);
        if(tag==null){
            throw new NotFoundException("该标签不存在");
        }
        model.addAttribute("tag",tag);
        return "admin/addtag";
    }
    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/add")
    public String tags(@Valid Tag type, BindingResult result, RedirectAttributes attributes){
        if (tagService.getTagByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            return "admin/addtag";
        }
        Tag t=tagService.saveTag(type);
        if (t!=null){
            attributes.addFlashAttribute("msg","添加成功");
        }else{
            attributes.addFlashAttribute("msg","添加失败");
        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/edit/{id}")
    public String edit(@Valid Tag type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if (tagService.getTagByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            return "admin/addtypes";
        }
        Tag t=tagService.updateTag(id,type);
        if (t!=null){
            attributes.addFlashAttribute("msg","修改成功");
        }else{
            attributes.addFlashAttribute("msg","修改失败");
        }
        return "redirect:/admin/tags";
    }
}

