package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private Long mediaId;

    @CreatedDate
    private Date created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Tag> tags;

    public Post(Long postId, Long mediaId, String username, Set<String> tags) {
        this.postId = postId;
        this.mediaId = mediaId;
        this.user = new User(username);
        this.tags = tags.stream().map(Tag::new).collect(Collectors.toSet());
    }

}