package rs.ac.uns.ftn.devops.tim5.nistagramsearch.mapper;

import rs.ac.uns.ftn.devops.tim5.nistagramsearch.dto.AdvertisementDTO;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Advertisement;

public class AdvertisementMapper {

    private AdvertisementMapper() {
    }

    public static AdvertisementDTO toDTO(Advertisement advertisement) {
        return new AdvertisementDTO(advertisement.getMediaId(), advertisement.getWebsiteUrl());
    }
}
