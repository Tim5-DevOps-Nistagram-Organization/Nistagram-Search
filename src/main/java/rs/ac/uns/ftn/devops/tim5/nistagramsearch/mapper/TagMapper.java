package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.TagDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Tag;

public class TagMapper {
    public static Tag toEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.getTitle());
    }
}
