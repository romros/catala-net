package cat.fundacio.catalanet.core.service;

import cat.fundacio.catalanet.core.model.UriEntity;

public interface UriEntityService {
    /**
     * Inserts or updates the given UriEntity in the database.
     * If the UriEntity already exists, it will be updated.
     * If the UriEntity does not exist, it will be inserted.
     *
     * @param input the UriEntity to insert or update with the date fields set
     * @return the inserted or updated UriEntity
     */

    public UriEntity upsert(String url, String title, String description, String language);
}
