package com.dosug.app.services.tags;

import com.dosug.app.domain.Tag;
import com.dosug.app.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleTagService implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public SimpleTagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private Tag createTag(String tagName) {

        tagName.toLowerCase();
        Tag tag = tagRepository.findByTag(tagName);

        // Если не удалось найти тег с таким названием создаём его.
        if (tag == null) {
            tag = new Tag(tagName);
            tagRepository.save(tag);
        }

        return tag;
    }

    @Override
    public Tag getTag(String tagName) {
        return createTag(tagName);
    }

    @Override
    public List<Tag> getTagsWithPart(String tagPart, Integer count) {
        //    tagRepository.
        return null;
    }
}