package com.cqw.cblog.Service;

import com.cqw.cblog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long  id);
    Type updateType(Long id,Type type);
    Page<Type> listType(Pageable pageable);
    List<Type> listTypes();
    void deleteType(Long id);
    Type getTypeByName(String name);
    List<Type> listTypesTop(Integer size);
}
