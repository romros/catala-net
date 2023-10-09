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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "T_SEARCH")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "QUERY_ID", referencedColumnName = "ID")
    private SearchQuery query;

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID", referencedColumnName = "ID")
    private Device device;

    @Column(name = "TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    @Column(name = "IP_ORIGIN")
    private String ipOrigin;

    @Column(name = "ACCEPT_LANGUAGE")
    private String acceptLanguage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "search", cascade = CascadeType.ALL)
    @OrderBy("position ASC") // Ordena ascendentment per la columna 'POSITION'
    private List<SearchResult> searchResults = new ArrayList<>();

    public Search() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SearchQuery getQuery() {
        return query;
    }

    public void setQuery(SearchQuery query) {
        this.query = query;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpOrigin() {
        return ipOrigin;
    }

    public void setIpOrigin(String ipOrigin) {
        this.ipOrigin = ipOrigin;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public String toString() {
        return "Search [id=" + id + ", query=" + query + ", device=" + device + ", timestamp=" + timestamp
                + ", ipOrigin=" + ipOrigin + ", acceptLanguage=" + acceptLanguage + "]";
    }

}
