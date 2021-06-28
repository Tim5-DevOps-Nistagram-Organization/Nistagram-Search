package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.ReactionRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

import java.util.Collection;

@Service
public class ReactionServiceImp implements ReactionService {

    private ReactionRepository reactionRepository;
    private UserService userService;
    private PostService postService;

    @Autowired
    public ReactionServiceImp(ReactionRepository reactionRepository,
                              PostService postService,
                              UserService userService) {
        this.reactionRepository = reactionRepository;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public Reaction findById(Long id) throws ResourceNotFoundException {
        return reactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction"));
    }

    @Override
    public Collection<Reaction> findMyAllReactions(String username, ReactionEnum reactionEnum) {
        return reactionRepository.findAllByUsernameAndReaction(username, reactionEnum);
    }

    @Override
    public Reaction addNewReaction(Reaction reaction) throws ResourceNotFoundException {
        reaction.setPost(postService.findByPostId(reaction.getPost().getPostId()));
        reaction.setUser(userService.findByUsername(reaction.getUser().getUsername()));
        return reactionRepository.save(reaction);
    }

    @Override
    public Reaction updateReaction(Reaction reaction) throws ResourceNotFoundException {
        Reaction old = reactionRepository.findByReactionId(reaction.getReactionId());
        old.setReaction(reaction.getReaction());
        return reactionRepository.save(old);
    }

    @Override
    public void deleteReaction(Long id) throws ResourceNotFoundException {
        Reaction r = reactionRepository.findByReactionId(id);
        reactionRepository.delete(r);
    }

}
