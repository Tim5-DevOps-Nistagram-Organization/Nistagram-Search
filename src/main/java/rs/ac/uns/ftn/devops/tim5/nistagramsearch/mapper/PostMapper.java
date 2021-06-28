package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;

public class PostMapper {

    public static PostResponseDTO fromReactionToPostResponseDTO(Reaction reaction) {
        return new PostResponseDTO(reaction.getPost().getPostId());
    }
}
