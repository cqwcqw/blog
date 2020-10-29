package com.cqw.cblog.Controller;

import com.cqw.cblog.NotFoundException;
import com.cqw.cblog.Service.TypeService;
import com.cqw.cblog.pojo.Tag;
import com.cqw.cblog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/add")
    public String add(Model model){
        model.addAttribute("type",new Type());
        return "admin/addtypes";
    }
    @GetMapping("/types/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Type t=typeService.getType(id);
        if(t==null){
            throw new NotFoundException("该分类不存在");
        }
        model.addAttribute("type",t);
        return "admin/addtypes";
    }
    @GetMapping("/types/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/types";
    }
    @PostMapping("/types/add")
    public String add(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if (typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            return "admin/addtypes";
        }
        Type t=typeService.saveType(type);
        if (t!=null){
            attributes.addFlashAttribute("msg","添加成功");
        }else{
            attributes.addFlashAttribute("msg","添加失败");
        }
        return "redirect:/admin/types";
    }
    @PostMapping("/types/edit/{id}")
    public String edit(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        if (typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            return "admin/addtypes";
        }
        Type t=typeService.updateType(id,type);
        if (t!=null){
            attributes.addFlashAttribute("msg","修改成功");
        }else{
            attributes.addFlashAttribute("msg","修改失败");
        }
        return "redirect:/admin/types";
    }
}

