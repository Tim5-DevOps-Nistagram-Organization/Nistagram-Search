package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostSearchDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Post;

import java.util.stream.Collectors;

public class PostMapper {

    public static Post toEntity(PostSearchDTO postDTO) {
        return new Post(postDTO.getPostId(), postDTO.getMediaId(),
                postDTO.getTags().stream().map(TagMapper::toEntity).collect(Collectors.toSet()));
    }
}
