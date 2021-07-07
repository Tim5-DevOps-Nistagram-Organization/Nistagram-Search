package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import org.springframework.data.domain.Page;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;

public interface ReactionService {

    Page<Reaction> findMyAllReactions(ReactionEnum reactionEnum, int numOfPage, int sizeOfPage, String username);

    Reaction addNewReaction(Reaction reaction) throws ResourceNotFoundException;

    void updateReaction(Reaction reaction);

    void deleteReaction(Long reactionId);


}
