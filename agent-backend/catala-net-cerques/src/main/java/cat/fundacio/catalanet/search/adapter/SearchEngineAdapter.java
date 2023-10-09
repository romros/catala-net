package cat.fundacio.catalanet.search.adapter;

import java.util.List;

import cat.fundacio.catalanet.search.model.SearchResultDTO;

public interface SearchEngineAdapter {

    List<SearchResultDTO> search(String queryText, String directory);

}
