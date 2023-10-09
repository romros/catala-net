// BEGIN: 9c8d5f8b8d7c
package cat.fundacio.catalanet.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeviceTest {

    private Device device;

    @BeforeEach
    public void setUp() {
        device = new Device("Test Device", "Test OS");
    }

    @Test
    public void testDeviceConstructor() {
        assertNotNull(device);
        assertEquals("Test Device", device.getDeviceName());
        assertEquals("Test OS", device.getOperatingSystem());
    }

    @Test
    public void testDeviceSearches() {
        // Inicialització del dispositiu
        Device device = new Device();
        device.setDeviceName("Test Device");
        device.setOperatingSystem("Test OS");

        // Creació de dues cerques
        SearchQuery query1 = new SearchQuery(null, "Test Search 1", true);
        SearchQuery query2 = new SearchQuery(null, "Test Search 2", true);

        Search search1 = createSearch(query1, device);
        Search search2 = createSearch(query2, device);

        // Afegir les cerques al dispositiu
        List<Search> searches = new ArrayList<>();
        searches.add(search1);
        searches.add(search2);
        device.setSearches(searches);

        // Verificar que el dispositiu té dues cerques
        assertEquals(2, device.getSearches().size());
    }

    // Constructor que falta en la classe Search:
    private Search createSearch(SearchQuery query, Device device) {
        Search search = new Search();
        search.setQuery(query);
        search.setDevice(device);
        search.setTimestamp(new Date());
        return search;
    }

    // comprovar que totes les llistes no son null sino 0 si al començament
    @Test
    public void testDeviceSearchesNotNull() {
        assertNotNull(device.getSearches());
        assertEquals(0, device.getSearches().size());
    }

}
// END: 9c8d5f8b8d7c