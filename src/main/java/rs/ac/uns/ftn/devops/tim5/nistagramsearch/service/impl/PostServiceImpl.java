package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.CanNotAccessException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.PostRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.TagService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

import java.security.Principal;

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
    public Post addNewPost(Post post) throws ResourceNotFoundException {
        post.setTags(tagService.getTagsForPost(post.getTags()));
        post.setUser(userService.findByUsername(post.getUser().getUsername()));
        return postRepository.save(post);
    }

    @Override
    public Post findByPostId(Long id) throws ResourceNotFoundException {
        return postRepository.findPostByOriginalPostId(id).orElseThrow(() -> new ResourceNotFoundException("Post"));
    }

    @Override
    public void deleteByPostId(Long id) throws ResourceNotFoundException {
        Post post = findByPostId(id);
        postRepository.deleteById(post.getId());
    }

    @Override
    public Page<Post> searchForAll(String tag, int numOfPage, int sizeOfPage) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return postRepository.findAllByTags_TitleAndUser_IsPrivate(tag, false, pageable);
    }

    @Override
    public Page<Post> search(String tag, int numOfPage, int sizeOfPage, String username) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return postRepository
                .findAllByTags_TitleAndUser_IsPrivateOrTags_TitleAndUser_Following_Username
                        (tag, false, tag, username, pageable);
    }

    @Override
    public Page<Post> homeForAll(int numOfPage, int sizeOfPage) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return postRepository.findAllByUser_IsPrivateOrderByCreatedDesc(false, pageable);
    }

    @Override
    public Page<Post> home(int numOfPage, int sizeOfPage, String username) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return postRepository
                .findAllByUser_IsPrivateOrUser_Following_UsernameOrderByCreatedDesc
                        (false, username, pageable);
    }

    @Override
    public Page<Post> searchUsersPost(String username, int numOfPage, int sizeOfPage, Principal principal) throws ResourceNotFoundException, CanNotAccessException {
        User user = userService.findByUsername(username);
        if (user.getIsPrivate() && (principal == null ||
                (user.getFollowing().stream().noneMatch(u -> u.getUsername().equals(principal.getName())) &&
                        !principal.getName().equals(username)))) {
            throw new CanNotAccessException("You can not see post of this user.");
        }

        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return postRepository.findAllByUser_UsernameOrderByCreatedDesc(username, pageable);
    }
}
