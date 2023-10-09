package cat.fundacio.catalanet.core.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import cat.fundacio.catalanet.core.config.CoreConfig;
import cat.fundacio.catalanet.core.model.Device;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = CoreConfig.class)
public class DeviceRepositoryTest {

    @Autowired
    private Device currentDevice; // Ara això serà injectat des de CoreConfig

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void whenFindByDeviceName_thenReturnDevice() {
        // Given
        Device device = currentDevice;

        // When
        Device found = deviceRepository.findByDeviceName(device.getDeviceName());

        // Then
        assertThat(found.getDeviceName()).isEqualTo(device.getDeviceName());
    }

    @Test
    public void whenFindById_thenReturnDevice() {
        // Given
        Device device = currentDevice;

        // When
        Optional<Device> found = deviceRepository.findById(device.getId());
        // Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getDeviceName()).isEqualTo(device.getDeviceName());
    }
}
