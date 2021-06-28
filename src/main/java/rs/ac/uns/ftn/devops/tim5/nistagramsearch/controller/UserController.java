package rs.ac.uns.ftn.devops.tim5.nistagramsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.UserDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.UserMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Page<UserDTO>> searchUser(@RequestParam String username,
                                                    @RequestParam int numOfPage,
                                                    @RequestParam int sizeOfPage) {
        return new ResponseEntity<>(userService.search(username, numOfPage, sizeOfPage).map(UserMapper::toDTO),
                HttpStatus.OK);

    }
}
