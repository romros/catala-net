package cat.fundacio.catalanet.core.service.intern;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.fundacio.catalanet.core.model.Device;
import cat.fundacio.catalanet.core.repository.DeviceRepository;
import cat.fundacio.catalanet.core.service.DeviceService;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private static Device currentDevice = null;

    @Autowired
    private DeviceRepository deviceRepository;

    public Device detectCurrentDevice() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();

        // Setting OS Information
        String operatingSystemInfo = os.getFamily() + " " + os.getVersionInfo().getVersion() + " "
                + System.getProperty("os.arch");
        // Setting Device Name
        String deviceNameInfo = hal.getComputerSystem().getModel();

        // Generate Unique Identifier based on multiple hardware factors
        String combinedInfo = hal.getComputerSystem().getSerialNumber() +
                hal.getProcessor().getProcessorIdentifier().getIdentifier();

        System.out.println("Combined Info: " + combinedInfo);

        String uniqueIdentifier = "NOID_" + UUID.randomUUID().toString();
        // Hash the combined string to produce the unique identifier
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combinedInfo.getBytes(StandardCharsets.UTF_8));
            uniqueIdentifier = bytesToHex(hash);
            System.out.println("Unique Identifier: " + uniqueIdentifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Creating the Device instance
        Device device = new Device();
        device.setDeviceName(deviceNameInfo);
        device.setOperatingSystem(operatingSystemInfo);
        device.setUniqueIdentifier(uniqueIdentifier);

        return device;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public Device getCurrentDevice() {
        if (DeviceServiceImpl.currentDevice == null) {

            // 1. Prova de trobar el dispositiu a la base de dades primer.
            List<Device> devicesInDatabase = (List<Device>) deviceRepository.findAll();

            if (!devicesInDatabase.isEmpty()) {
                // Agafa el primer dispositiu de la llista. Suposant que només hi haurà un.
                DeviceServiceImpl.currentDevice = devicesInDatabase.get(0);
            } else {
                // 2. Si no es troba cap dispositiu a la base de dades, detecta l'actual i
                // després el guarda.
                DeviceServiceImpl.currentDevice = detectCurrentDevice();
                deviceRepository.save(DeviceServiceImpl.currentDevice);
            }
        }
        return DeviceServiceImpl.currentDevice;
    }

}
