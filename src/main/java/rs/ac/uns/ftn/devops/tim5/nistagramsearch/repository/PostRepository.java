package rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.postId= ?1")
    Optional<Post> findPostByOriginalPostId(Long id);

    @Query("SELECT DISTINCT p FROM Post p INNER JOIN p.tags t WHERE p.user.isPrivate = false and t.title = ?1")
    Page<Post> findAllTags(String title, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p INNER JOIN p.tags t INNER JOIN p.user.following f " +
            "WHERE (p.user.isPrivate = false and t.title = ?1) or " +
            "(p.user.isPrivate = true and f.username = ?2 and t.title = ?1 )")
    Page<Post> findAllTagsByUser(String title, String username, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p WHERE p.user.isPrivate = false order by p.created desc")
    Page<Post> findAllHome(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p INNER JOIN p.user.following f WHERE p.user.isPrivate = false or " +
            "(p.user.isPrivate = true and f.username = ?1) order by p.created desc")
    Page<Post> findAllHomeByUser(String username, Pageable pageable);

    Page<Post> findAllByUser_UsernameOrderByCreatedDesc(String username, Pageable pageable);
}
