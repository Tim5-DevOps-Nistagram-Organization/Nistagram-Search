package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.Constants;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.UserRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(String username, String email, String websiteUrl) {
        User user = new User(username, email, websiteUrl);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) throws ResourceNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    @Override
    public void setSettings(String username, boolean isPrivate) throws ResourceNotFoundException {
        User user = findByUsername(username);
        user.setIsPrivate(isPrivate);
        userRepository.save(user);
    }

    @Override
    public Page<User> search(String username, int numOfPage, int sizeOfPage) {
        Pageable pageable = PageRequest.of(numOfPage, sizeOfPage);
        return userRepository.findAllByUsernameContains(username, pageable);
    }

    @Override
    public void follow(String userUsername, String followingUsername, String followAction) throws ResourceNotFoundException {
        User user = findByUsername(userUsername);
        User followingUser = findByUsername(followingUsername);
        switch (followAction) {
            case Constants.FOLLOW_ACTION:
                user.getFollowing().add(followingUser);
                break;
            case Constants.UNFOLLOW_ACTION:
                user.setFollowing(user.getFollowing().stream()
                        .filter(u -> !u.getUsername().equals(followingUsername))
                        .collect(Collectors.toSet()));
                user.setMuted(user.getMuted().stream()
                        .filter(u -> !u.getUsername().equals(followingUsername))
                        .collect(Collectors.toSet()));
                break;
            case Constants.MUTE_ACTION:
                if (user.getFollowing().stream().anyMatch(u -> u.getUsername().equals(followingUsername)))
                    user.getMuted().add(followingUser);
                break;
            case Constants.UNMUTE_ACTION:
                user.setMuted(user.getMuted().stream()
                        .filter(u -> !u.getUsername().equals(followingUsername))
                        .collect(Collectors.toSet()));
                break;
        }
        userRepository.save(user);
    }

}
