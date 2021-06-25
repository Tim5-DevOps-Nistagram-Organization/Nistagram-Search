package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Tag> tags;

    public Post(Long postId, Long mediaId, Set<Tag> tags) {
        this.postId = postId;
        this.mediaId = mediaId;
        this.tags = tags;
    }

}