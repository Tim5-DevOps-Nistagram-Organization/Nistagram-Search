package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.UserRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(String username, String email) {
        User user = new User(username, email);
        User userSaved = userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User"));
        userRepository.delete(user);
    }
}
