package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;

public interface PostService {
    void addNewPost(Post post) throws ResourceNotFoundException;
    Post findById(Long id) throws ResourceNotFoundException;
    Post findByPostId(Long id) throws ResourceNotFoundException;
}
