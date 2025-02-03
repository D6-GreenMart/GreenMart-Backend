package com.greenmart.app.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.greenmart.app.domain.entities.Tag;
import com.greenmart.app.repositories.TagRepository;
import com.greenmart.app.services.TagService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	@Override
	public List<Tag> getTags() {
		return tagRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public List<Tag> createTags(Set<String> tagNames) {
		List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
		Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());
		List<Tag> newTags = tagNames.stream()
		.filter(name -> !existingTagNames.contains(name))
		.map(Tag::new)
		.collect(Collectors.toList());
		
		List<Tag> savedTags = new ArrayList<>();
	    if(!newTags.isEmpty()) {
	        savedTags = tagRepository.saveAll(newTags);
	    }

	    savedTags.addAll(existingTags);

	    return savedTags;
	}

	@Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getProducts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        });
    }

}
