package com.dosug.app.repository;


import com.dosug.app.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
