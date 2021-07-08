package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Reaction;

public class PostMapper {

    private PostMapper() {
    }

    public static PostResponseDTO fromReactionToPostResponseDTO(Reaction reaction) {
        return new PostResponseDTO(reaction.getPost().getPostId(), reaction.getPost().getMediaId());
    }

    public static PostResponseDTO toDto(Post post) {
        return new PostResponseDTO(post.getPostId(), post.getMediaId());
    }
}
