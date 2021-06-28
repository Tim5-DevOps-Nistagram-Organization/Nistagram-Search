package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums;

import java.util.stream.Stream;

public enum ReactionEnum {
    LIKE(1), DISLIKE(2), FAVOURITES(3);

    private int value;

    private ReactionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReactionEnum of(int value) {
        return Stream.of(ReactionEnum.values())
                .filter(p -> p.getValue() == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}