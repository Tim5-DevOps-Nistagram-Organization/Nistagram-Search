package rs.ac.uns.ftn.devops.tim5.nistagramsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.PostMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class SearchPostController {

    private final ReactionService reactionService;

    @Autowired
    public SearchPostController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PreAuthorize("hasRole('ROLE_REGULAR') || hasRole('ROLE_AGENT')")
    @GetMapping(value = "/{myReaction}")
    public ResponseEntity<Collection<PostResponseDTO>> findAllMyByReaction(@PathVariable int myReaction, Principal principal) {
        ReactionEnum reactionEnum = ReactionEnum.of(myReaction);
        Collection<PostResponseDTO> postResponseDTOS =
                reactionService.findMyAllReactions(principal.getName(), reactionEnum)
                        .stream().map(r -> PostMapper.fromReactionToPostResponseDTO(r))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(postResponseDTOS, HttpStatus.OK);

    }
}
