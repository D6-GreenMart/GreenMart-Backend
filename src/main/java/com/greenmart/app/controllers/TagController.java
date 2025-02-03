package com.greenmart.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CreateTagsRequest;
import com.greenmart.app.domain.dtos.TagResponse;
import com.greenmart.app.domain.entities.Tag;
import com.greenmart.app.mappers.TagMapper;
import com.greenmart.app.services.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
	
	private final TagService tagService;
	private final TagMapper tagMapper;
	
	@GetMapping
	public ResponseEntity<List<TagResponse>> getAllTags(){
		List<Tag> tags = tagService.getTags();
		List<TagResponse> tagResponses = tags.stream().map(tag -> tagMapper.toTagResponse(tag)).toList();
		return ResponseEntity.ok(tagResponses);
	}
	
	@PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
        List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
        List<TagResponse> createdTagResponse = savedTags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(
                createdTagResponse,
                HttpStatus.CREATED
        );
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
	    tagService.deleteTag(id);
	    return ResponseEntity.noContent().build();
	}
}
