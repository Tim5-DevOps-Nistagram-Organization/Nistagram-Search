package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.ReactionRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class ReactionServiceImp implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public ReactionServiceImp(ReactionRepository reactionRepository,
                              PostService postService,
                              UserService userService) {
        this.reactionRepository = reactionRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Page<Reaction> findMyAllReactions(ReactionEnum reactionEnum, int numOfPage, int sizeOfPage, String username) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return reactionRepository.findAllByUsernameAndReaction(username, reactionEnum, pageable);
    }

    @Override
    public Reaction addNewReaction(Reaction reaction) throws ResourceNotFoundException {
        reaction.setPost(postService.findByPostId(reaction.getPost().getPostId()));
        reaction.setUser(userService.findByUsername(reaction.getUser().getUsername()));
        return reactionRepository.save(reaction);
    }

    @Override
    public void updateReaction(Reaction reaction) {
        Reaction old = reactionRepository.findByReactionId(reaction.getReactionId());
        old.setReaction(reaction.getReaction());
        reactionRepository.save(old);
    }

    @Override
    public void deleteReaction(Long id) {
        Reaction r = reactionRepository.findByReactionId(id);
        reactionRepository.delete(r);
    }

}
