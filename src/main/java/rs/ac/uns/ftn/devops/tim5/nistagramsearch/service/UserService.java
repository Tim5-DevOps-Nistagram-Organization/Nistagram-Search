package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service;

public interface UserService {
    void create(String username, String email);

    void delete(String username);
}
