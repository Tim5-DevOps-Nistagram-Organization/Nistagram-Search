package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import org.springframework.data.domain.Page;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;

public interface UserService {
    void create(String username, String email, String websiteUrl);

    User findByUsername(String username) throws ResourceNotFoundException;

    void setSettings(String username, boolean isPrivate) throws ResourceNotFoundException;

    Page<User> search(String username, int numOfPage, int sizeOfPage);

    void follow(String userUsername, String followingUsername, String followAction) throws ResourceNotFoundException;
}
