package com.greenmart.app.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.greenmart.app.domain.entities.Tag;

public interface TagService {
	List<Tag> getTags();
	List<Tag> createTags(Set<String> names);
	void deleteTag(UUID id);
}
