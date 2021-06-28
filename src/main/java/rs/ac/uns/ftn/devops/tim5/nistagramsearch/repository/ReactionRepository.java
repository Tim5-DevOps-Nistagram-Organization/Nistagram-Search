package rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;

import java.util.Collection;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT r FROM Reaction r WHERE r.user.username = ?1")
    Collection<Reaction> findAllByUser(String username);

    @Query("SELECT r FROM Reaction r WHERE r.user.username = ?1 and r.reaction = ?2")
    Collection<Reaction> findAllByUsernameAndReaction(String username, ReactionEnum reactionEnum);

    @Query("SELECT r FROM Reaction r WHERE r.reactionId = ?1")
    Reaction findByReactionId(Long id);
}
