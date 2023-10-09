package cat.fundacio.catalanet.core.repository;

import org.springframework.data.repository.CrudRepository;

import cat.fundacio.catalanet.core.model.Device;

public interface DeviceRepository extends CrudRepository<Device, Long> {
    public Device findByDeviceName(String deviceName);

    public Device findByUniqueIdentifier(String uniqueIdentifier);

}
