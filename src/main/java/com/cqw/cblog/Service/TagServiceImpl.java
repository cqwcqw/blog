package com.cqw.cblog.Service;

import com.cqw.cblog.DAO.TagRepository;
import com.cqw.cblog.NotFoundException;
import com.cqw.cblog.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    
    @Transactional
    @Override
    public Tag saveTag(Tag Tag) {
        return tagRepository.save(Tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public List<Tag> listTagsTop(Integer size) {
        Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }
    @Transactional
    @Override
    public List<Tag> matchTag(String ids) {
        List<Tag> tags=new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+");
        for (String s:toSList(ids)) {
            if (pattern.matcher(s).matches()){
                tags.add(tagRepository.findById(Long.valueOf(s)).orElse(new Tag()));
            }else{
                Tag tag=new Tag();
                tag.setName(s);
                tagRepository.save(tag);
                tags.add(tagRepository.findByName(s));
            }
        }
        return tags;
    }
    @Override
    public List<Tag> listTags(String ids) {
        return tagRepository.findAllById(toList(ids));
    }

    @Override
    public List<Tag> listTags() {
        return tagRepository.findAll();
    }
    public List<Long> toList(String ids){
        List<Long> list =new ArrayList<>();
        if (!"".equals(ids)&& ids!=null){
            String[] idss=ids.split(",");
            for (int i=0;i<idss.length;i++){
                list.add(new Long(idss[i]));
            }
        }
        return list;
    }
    public List<String> toSList(String ids){
        List<String> list =new ArrayList<>();
        if (!"".equals(ids)&& ids!=null){
            String[] idss=ids.split(",");
            for (int i=0;i<idss.length;i++){
                list.add(idss[i]);
            }
        }
        return list;
    }
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag Tag) {
        Tag t=tagRepository.getOne(id);
        if (t==null){
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(Tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
