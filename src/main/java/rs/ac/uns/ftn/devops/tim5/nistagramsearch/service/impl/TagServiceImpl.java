package rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.Tag;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.repository.TagRepository;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.service.TagService;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> getTagsForPost(Set<Tag> tags) {
        HashSet<Tag> tagsSet = new HashSet<>();
        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagRepository.findByTitle(tag.getTitle());
            Tag newTag = optionalTag.orElseGet(() -> tagRepository.save(new Tag(null, tag.getTitle())));
            tagsSet.add(newTag);
        }
        return tagsSet;
    }
}
