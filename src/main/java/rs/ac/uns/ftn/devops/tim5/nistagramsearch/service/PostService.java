package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import org.springframework.data.domain.Page;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.CanNotAccessException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;

import java.security.Principal;

public interface PostService {
    Post addNewPost(Post post) throws ResourceNotFoundException;

    Post findByPostId(Long id) throws ResourceNotFoundException;

    void deleteByPostId(Long id) throws ResourceNotFoundException;

    Page<Post> searchForAll(String tag, int numOfPage, int sizeOfPage);

    Page<Post> search(String tag, int numOfPage, int sizeOfPage, String name);

    Page<Post> homeForAll(int numOfPage, int sizeOfPage);

    Page<Post> home(int numOfPage, int sizeOfPage, String name);

    Page<Post> searchUsersPost(String username, int numOfPage, int sizeOfPage, Principal principal) throws ResourceNotFoundException, CanNotAccessException;
}
