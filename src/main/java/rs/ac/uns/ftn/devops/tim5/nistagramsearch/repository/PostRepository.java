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

    Page<Post> findAllByTags_TitleAndUser_IsPrivate(String title, boolean isPrivate, Pageable pageable);

    Page<Post> findAllByTags_TitleAndUser_IsPrivateOrTags_TitleAndUser_Following_Username(String tags_title, Boolean user_isPrivate, String tags_title2, String user_following_username, Pageable pageable);

    Page<Post> findAllByUser_IsPrivateOrderByCreatedDesc(boolean isPrivate, Pageable pageable);

    Page<Post> findAllByUser_IsPrivateOrUser_Following_UsernameOrderByCreatedDesc(Boolean user_isPrivate, String user_following_username, Pageable pageable);

    Page<Post> findAllByUser_UsernameOrderByCreatedDesc(String username, Pageable pageable);
}
