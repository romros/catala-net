package cat.fundacio.catalanet.core.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_SEARCH_RESULTS")
public class SearchResult {
    @Override
    public String toString() {
        return "SearchResult [id=" + id + ", search=" + search + ", uri=" + uri + ", position=" + position + "]";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SEARCH_ID", referencedColumnName = "ID", nullable = false)
    private Search search;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "URI_ID", referencedColumnName = "ID", nullable = false)
    private UriEntity uri;

    @Column(name = "POSITION", nullable = false)
    private Integer position;

    public SearchResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public UriEntity getUri() {
        return uri;
    }

    public void setUri(UriEntity uri) {
        this.uri = uri;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
