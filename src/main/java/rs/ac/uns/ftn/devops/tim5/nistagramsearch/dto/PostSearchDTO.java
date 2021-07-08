package rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchDTO {
    private Long postId;
    private Long mediaId;
    private Set<TagDTO> tags;
}
