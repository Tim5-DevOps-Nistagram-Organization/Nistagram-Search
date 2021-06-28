package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.PostRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.TagService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagService tagService, UserService userService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public void addNewPost(Post post) throws ResourceNotFoundException {
        post.setTags(tagService.getTagsForPost(post.getTags()));
        post.setUser(userService.findByUsername(post.getUser().getUsername()));
        postRepository.save(post);
    }

    @Override
    public Post findById(Long id) throws ResourceNotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    @Override
    public Post findByPostId(Long id) throws ResourceNotFoundException {
        return postRepository.findPostByOriginalPostId(id).orElseThrow(() -> new ResourceNotFoundException("User"));
    }
}
