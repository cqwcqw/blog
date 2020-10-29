package com.cqw.cblog.Service;

import com.cqw.cblog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag Tag);
    Tag getTag(Long  id);
    Tag updateTag(Long id,Tag Tag);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTags();
    List<Tag> listTags(String ids);
    void deleteTag(Long id);
    Tag getTagByName(String name);
    List<Tag> matchTag(String ids);
    List<Tag> listTagsTop(Integer size);
}
