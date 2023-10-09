package cat.fundacio.catalanet.core.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import cat.fundacio.catalanet.core.model.SearchQuery;

public interface SearchQueryRepository extends CrudRepository<SearchQuery, Long> {
    // find all queries that active is true
    public List<SearchQuery> findByActiveTrue();

    // agafa el primer per queryText
    public SearchQuery findByQueryText(String queryText);

}
