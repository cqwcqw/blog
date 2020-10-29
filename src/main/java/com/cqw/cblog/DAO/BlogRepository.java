package com.cqw.cblog.DAO;

import com.cqw.cblog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query(value="select b from t_blog b where b.recommend=true")
    List<Blog> findTop(Pageable p);

    @Query(value="select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="update t_blog b set b.view = b.view+1 where b.id = ?1")
    int updateViews(Long id);

    @Query(value="select b from t_blog b where b.id = ?1")
    Blog findOne(Long id);


    @Query(value="select function('date_format',b.updateTime,'%Y')  from t_blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc ")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
