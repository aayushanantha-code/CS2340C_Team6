public class Community {
    private String communityID;
    private String name;

    /**
     * Constructor for Community
     * @param communityID Name of the community
     * @param name Name of the community
     */
    public Community(String communityID, String name) {
        this.communityID = communityID;
        this.name = name;
    }

    public String getCommunityID() {
        return this.communityID;
    }

    public String getName() {
        return this.name;
    }

    public void setCommunityID(String communityID) {
        this.communityID = community;
    }

    public void setName(String name) {
        this.name = name;
    }
}
