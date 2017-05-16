package com.dosug.app.repository;


import com.dosug.app.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, String> {

    Tag findByTagName(String tagName);

    Page<Tag> findByTagNameStartingWith(String tagName, Pageable pageable);
}
