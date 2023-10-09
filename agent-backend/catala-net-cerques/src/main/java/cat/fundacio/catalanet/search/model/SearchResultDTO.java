package cat.fundacio.catalanet.search.model;

public class SearchResultDTO {
    private String title;
    private String description;
    private String url;
    private Integer position;
    private String language;

    public SearchResultDTO() {
    }

    public SearchResultDTO(String title, String description, String url, Integer position, String language) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.position = position;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
