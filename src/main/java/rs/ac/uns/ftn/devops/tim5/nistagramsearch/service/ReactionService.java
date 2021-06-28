package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;

import java.util.Collection;

public interface ReactionService {

    Reaction findById(Long id) throws ResourceNotFoundException;
    Collection<Reaction> findMyAllReactions(String username, ReactionEnum reactionEnum);

    Reaction addNewReaction(Reaction reaction) throws ResourceNotFoundException;
    Reaction updateReaction(Reaction reaction) throws ResourceNotFoundException;
    void deleteReaction(Long reactionId) throws ResourceNotFoundException;



}
