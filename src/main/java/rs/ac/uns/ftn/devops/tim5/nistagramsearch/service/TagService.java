package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Tag;

import java.util.Set;

public interface TagService {
    Set<Tag> getTagsForPost(Set<Tag> tags);
}
