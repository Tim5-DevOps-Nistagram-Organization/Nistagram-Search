package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.PostResponseDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Advertisement;

public class AdvertismentMapper {

    private AdvertismentMapper(){}

    public static PostResponseDTO toSearchDTO(Advertisement advertisement) {
        return new PostResponseDTO(advertisement.getId(), Long.parseLong(advertisement.getMediaId()));
    }
}
