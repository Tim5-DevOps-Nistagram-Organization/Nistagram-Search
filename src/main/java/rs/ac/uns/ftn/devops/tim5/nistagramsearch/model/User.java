package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPrivate;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username) {
        this.username = username;
    }
}
