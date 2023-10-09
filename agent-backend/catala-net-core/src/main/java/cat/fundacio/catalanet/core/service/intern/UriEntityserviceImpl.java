package cat.fundacio.catalanet.core.service.intern;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.fundacio.catalanet.core.model.UriEntity;
import cat.fundacio.catalanet.core.repository.UriEntityRepository;
import cat.fundacio.catalanet.core.service.UriEntityService;

@Service("uriEntityService")
public class UriEntityserviceImpl implements UriEntityService {
    @Autowired
    private UriEntityRepository uriEntityRepository;

    // donat les dades d'una uri: url, title, description, language
    // agafa l'element de la base de dades que tingui la mateixa url
    // si no existeix, el crea
    // si existeix, actualitza les dades necessàries
    public UriEntity upsert(String url, String title, String description, String language) {
        UriEntity existingUri = uriEntityRepository.findByUrl(url);

        if (existingUri == null) { // Si no existeix, el creem
            UriEntity newUri = new UriEntity();
            newUri.setUrl(url);
            newUri.setTitle(title);
            newUri.setDescription(description);
            newUri.setLanguage(language);
            newUri.setFirstFound(new Date()); // la data actual
            newUri.setLastModified(new Date()); // la data actual
            return uriEntityRepository.save(newUri);
        } else { // Si ja existeix, actualitzem les dades necessàries
            boolean changed = false;

            if (!Objects.equals(existingUri.getTitle(), title)) {
                existingUri.setTitle(title);
                changed = true;
            }

            if (!Objects.equals(existingUri.getDescription(), description)) {
                existingUri.setDescription(description);
                changed = true;
            }

            if (!Objects.equals(existingUri.getLanguage(), language)) {
                existingUri.setLanguage(language);
                changed = true;
            }

            if (changed) {
                existingUri.setLastModified(new Date()); // actualitzem la data de la última modificació
                return uriEntityRepository.save(existingUri);
            }
        }

        return existingUri;
    }
}
