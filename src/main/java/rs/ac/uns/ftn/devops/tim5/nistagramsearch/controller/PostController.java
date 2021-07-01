package rs.ac.uns.ftn.devops.tim5.nistagramsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.CanNotAccessException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.PostMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final ReactionService reactionService;

    @Autowired
    public PostController(PostService postService, ReactionService reactionService) {
        this.postService = postService;
        this.reactionService = reactionService;
    }

    @GetMapping(value = "/home")
    public ResponseEntity<Page<PostResponseDTO>> home(@RequestParam int numOfPage,
                                                      @RequestParam int sizeOfPage,
                                                      Principal principal) {
        if (principal == null)
            return new ResponseEntity<>(
                    postService.homeForAll(numOfPage, sizeOfPage)
                            .map(PostMapper::toDto),
                    HttpStatus.OK);
        return new ResponseEntity<>(
                postService.home(numOfPage, sizeOfPage, principal.getName())
                        .map(PostMapper::toDto),
                HttpStatus.OK);
    }

    @GetMapping(value = "/tag")
    public ResponseEntity<Page<PostResponseDTO>> searchByTag(@RequestParam String tag,
                                                             @RequestParam int numOfPage,
                                                             @RequestParam int sizeOfPage,
                                                             Principal principal) {
        if (principal == null)
            return new ResponseEntity<>(
                    postService.searchForAll(tag, numOfPage, sizeOfPage)
                            .map(PostMapper::toDto),
                    HttpStatus.OK);
        return new ResponseEntity<>(
                postService.search(tag, numOfPage, sizeOfPage, principal.getName())
                        .map(PostMapper::toDto),
                HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Page<PostResponseDTO>> searchByUser(@RequestParam String username,
                                                              @RequestParam int numOfPage,
                                                              @RequestParam int sizeOfPage,
                                                              Principal principal) throws CanNotAccessException, ResourceNotFoundException {

        return new ResponseEntity<>(
                postService.searchUsersPost(username, numOfPage, sizeOfPage, principal)
                        .map(PostMapper::toDto),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_REGULAR') || hasRole('ROLE_AGENT')")
    @GetMapping(value = "/reaction/{myReaction}")
    public ResponseEntity<Collection<PostResponseDTO>> findAllMyByReaction(@PathVariable int myReaction, Principal principal) {
        ReactionEnum reactionEnum = ReactionEnum.of(myReaction);
        Collection<PostResponseDTO> postResponseDTOS =
                reactionService.findMyAllReactions(principal.getName(), reactionEnum)
                        .stream().map(PostMapper::fromReactionToPostResponseDTO)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(postResponseDTOS, HttpStatus.OK);

    }
}
