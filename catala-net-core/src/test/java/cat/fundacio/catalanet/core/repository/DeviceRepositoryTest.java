package cat.fundacio.catalanet.core.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import cat.fundacio.catalanet.core.boot.ClientmonitorApplication;
import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.service.DeviceService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = ClientmonitorApplication.class) // Aquesta és la classe amb @SpringBootApplication
public class DeviceRepositoryTest {

    @MockBean
    private Device currentDevice;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    // fes un setUp() per aquesta classe
    @BeforeEach
    public void setUp() {
        // Fes una instancei de deviceServiceImpl
        this.currentDevice = deviceService.detectCurrentDevice();
    }

    @Test
    public void whenFindByDeviceName_thenReturnDevice() {
        // Given
        Device device = currentDevice;
        entityManager.persist(device);
        entityManager.flush();

        // When
        Device found = deviceRepository.findByDeviceName(device.getDeviceName());

        // Then
        assertThat(found.getDeviceName()).isEqualTo(device.getDeviceName());
    }

    @Test
    public void whenFindById_thenReturnDevice() {
        // Given
        Device device = currentDevice;
        entityManager.persist(device);
        // When
        Optional<Device> found = deviceRepository.findById(device.getId());
        // Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getDeviceName()).isEqualTo(device.getDeviceName());
    }

}
