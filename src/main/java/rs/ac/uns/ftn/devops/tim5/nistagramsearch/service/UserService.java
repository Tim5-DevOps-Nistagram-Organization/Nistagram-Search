package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;

public interface UserService {
    void create(String username, String email);

    User findByUsername(String username) throws ResourceNotFoundException;

    void setSettings(String username, boolean isPrivate) throws ResourceNotFoundException;
}
