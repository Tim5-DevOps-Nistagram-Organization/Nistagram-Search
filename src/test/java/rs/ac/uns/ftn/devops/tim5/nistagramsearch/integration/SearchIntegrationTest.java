package rs.ac.uns.ftn.devops.tim5.nistagramsearch.integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.constants.*;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.UserDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.PostMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper.UserMapper;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums.ReactionEnum;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.PostService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.ReactionService;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.UserService;

import javax.transaction.Transactional;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class SearchIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ReactionService reactionService;

    /*
    *  Method test User search
    *  Should return user with username UserConstants.USERNAME1 as only search results
    *
    * */
    @Test
    public void testProfileSearch_Success() throws URISyntaxException {

        // create user for Search
        userService.create(UserConstants.USERNAME1, UserConstants.EMAIL, "");

        // test Profile search
        Page<UserDTO> retVal =  userService
                .search(UserConstants.USERNAME1, 0, 10).map(UserMapper::toDTO);

        assertEquals(1, retVal.getTotalElements());

        UserDTO retValUser = retVal.get().collect(Collectors.toList()).get(0);
        assertEquals(UserConstants.USERNAME1, retValUser.getUsername());

    }

    /*
    *   Method test Post search by tag
    *
    *   Create post with tag for search
    *
    *   Should return previous created post
    *
    * */
    @Test
    public void testTagSearchForAll_Success() throws URISyntaxException, ResourceNotFoundException {


        // create post with tags
        Set<String> tags = new HashSet<>();
        tags.add(TagConstants.TAG1);

        userService.create(UserConstants.USERNAME2, UserConstants.EMAIL, "");
        Post post = new Post(PostConstants.VALID_POST_ID,
                MediaConstants.VALID_ID,
                UserConstants.USERNAME2,
                tags);

        post = postService.addNewPost(post);

        //search post by tag
        Page<PostResponseDTO> retVal = postService.searchForAll(TagConstants.TAG1, 0, 10)
                        .map(PostMapper::toDto);

        assertEquals(1, retVal.getTotalElements());
        PostResponseDTO retValPost = retVal.get().collect(Collectors.toList()).get(0);
        assertEquals(post.getId(), retValPost.getPostId());
        assertEquals(post.getMediaId(), retValPost.getMediaId());

    }

    /*
     *   Method test find all Like reactions
     *
     *   Method test creation of Like Reaction for Post with tags
     *
     *   Search results by reaction should return only this created post
     *
     * */
    @Test
    public void testFindReactionsForPost_Success() throws ResourceNotFoundException {


        // create post with tags
        Set<String> tags = new HashSet<>();
        tags.add(TagConstants.TAG1);

        userService.create(UserConstants.USERNAME3, UserConstants.EMAIL, "");
        Post post = new Post(PostConstants.VALID_POST_ID,
                MediaConstants.VALID_ID,
                UserConstants.USERNAME3,
                tags);

        post = postService.addNewPost(post);


        // test create Reaction
        Reaction reaction = new Reaction(
                ReactionConstants.VALID_ID,
                ReactionEnum.LIKE,
                post.getPostId(),
                UserConstants.USERNAME3);

        reaction = reactionService.addNewReaction(reaction);
        assertNotNull(reaction.getId());
        assertEquals(reaction.getReaction().getValue(), ReactionEnum.LIKE.getValue());
        assertEquals(reaction.getUser().getUsername(), UserConstants.USERNAME3);


        Collection<PostResponseDTO> postResponseDTOS =
                reactionService.findMyAllReactions(UserConstants.USERNAME3, ReactionEnum.LIKE)
                        .stream().map(PostMapper::fromReactionToPostResponseDTO)
                        .collect(Collectors.toList());


        assertEquals(1, postResponseDTOS.size());
        PostResponseDTO responseDTO = postResponseDTOS.stream().collect(Collectors.toList()).get(0);
        assertEquals(reaction.getPost().getPostId(), responseDTO.getPostId());
        assertEquals(reaction.getPost().getMediaId(), responseDTO.getMediaId());

    }




}
