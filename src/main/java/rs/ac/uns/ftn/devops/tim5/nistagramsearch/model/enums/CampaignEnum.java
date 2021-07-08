package rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.enums;

public enum CampaignEnum {
    SINGLE("SINGLE"), MULTIPLE("MULTIPLE");
    private final String name;

    CampaignEnum(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
