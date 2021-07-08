package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.UserDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername());
    }
}
