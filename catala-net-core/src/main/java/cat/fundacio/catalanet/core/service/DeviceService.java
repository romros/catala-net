package cat.fundacio.catalanet.core.service;

import cat.fundacio.catalanet.core.model.Device;

public interface DeviceService {
    public Device detectCurrentDevice();

    public Device getCurrentDevice();
}
