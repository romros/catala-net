package cat.fundacio.catalanet.core.repository;

import org.springframework.data.repository.CrudRepository;

import cat.fundacio.catalanet.core.model.UriEntity;

public interface UriEntityRepository extends CrudRepository<UriEntity, Long> {

    UriEntity findByUrl(String url);

}
