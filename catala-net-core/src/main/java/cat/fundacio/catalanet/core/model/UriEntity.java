package cat.fundacio.catalanet.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "T_URIs")
public class UriEntity { // 'URI' és una classe estàndard en Java, així que utilitzem un nom diferent

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "URL", nullable = false, unique = true)
    private String url;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "FIRST_FOUND", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstFound = new Date();

    @Column(name = "LAST_MODIFIED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uri", cascade = CascadeType.ALL)
    private List<SearchResult> searchResults = new ArrayList<>();

    public UriEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getFirstFound() {
        return firstFound;
    }

    public void setFirstFound(Date firstFound) {
        this.firstFound = firstFound;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public String toString() {
        return "UriEntity [id=" + id + ", url=" + url + ", title=" + title + ", description=" + description
                + ", language=" + language + ", firstFound=" + firstFound + ", lastModified=" + lastModified + "]";
    }
}
