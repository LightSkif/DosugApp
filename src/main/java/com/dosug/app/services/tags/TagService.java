package com.dosug.app.services.tags;

import com.dosug.app.domain.Tag;

import java.util.List;


public interface TagService {

    Tag getTag(String tagName);

    List<Tag> getTagsWithPart(String tagPart, int count);
}
