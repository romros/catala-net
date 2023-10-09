package cat.fundacio.catalanet.core.model;

import java.util.ArrayList;
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

@Entity
@Table(name = "T_QUERY")
public class SearchQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "QUERY_TEXT", nullable = false, unique = true, length = 255)
    private String queryText;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "query", cascade = CascadeType.ALL)
    private List<Search> searches = new ArrayList<>();

    public SearchQuery(Long id, String queryText, Boolean active) {
        this.id = id;
        this.queryText = queryText;
        this.active = active;
    }

    public SearchQuery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

}
