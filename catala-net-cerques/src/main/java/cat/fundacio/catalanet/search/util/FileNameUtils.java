package cat.fundacio.catalanet.search.util;

public class FileNameUtils {
    public static String sanitizeFileName(String queryText) {
        // Utilitzem una expressió regular per eliminar caràcters no desitjats del nom
        // del fitxer
        return queryText.replaceAll("[^a-zA-Z0-9\\-_]", "_");
    }

}
