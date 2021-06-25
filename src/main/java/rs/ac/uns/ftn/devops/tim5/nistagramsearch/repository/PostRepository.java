package rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
