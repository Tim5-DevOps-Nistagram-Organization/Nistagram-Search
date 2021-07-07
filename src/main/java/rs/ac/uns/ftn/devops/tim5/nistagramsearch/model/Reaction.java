package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reactionId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private ReactionEnum reaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    public Reaction(Long reactionId, ReactionEnum reaction, Long postId, String username) {
        this.reactionId = reactionId;
        this.user = new User(username);
        this.reaction = reaction;
        this.post = new Post(postId);
    }

}
