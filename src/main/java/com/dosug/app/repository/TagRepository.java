package com.dosug.app.repository;


import com.dosug.app.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, String> {

    Tag findByTag(String tagName);
}
