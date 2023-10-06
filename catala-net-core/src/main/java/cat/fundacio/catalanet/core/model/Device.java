package cat.fundacio.catalanet.core.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a device with a name and an operating system.
 */
@Entity
@Table(name = "T_DEVICE")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DEVICE_NAME", nullable = false)
    private String deviceName;

    @Column(name = "OPERATING_SYSTEM", nullable = false)
    private String operatingSystem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device", cascade = CascadeType.ALL)
    private List<Search> searches = new ArrayList<>();

    @Column(name = "UNIQUE_IDENTIFIER", nullable = false, unique = true)
    private String uniqueIdentifier;

    /**
     * Represents a device in the monitoring system.
     */
    public Device() {
        // Constructor per defecte
    }

    /**
     * Constructs a new Device object with the given device name and operating
     * system.
     *
     * @param deviceName      the name of the device
     * @param operatingSystem the operating system of the device
     */
    public Device(String deviceName, String operatingSystem) {
        this.deviceName = deviceName;
        this.operatingSystem = operatingSystem;
    }

    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the device.
     * 
     * @param i the ID to set
     */
    public void setId(Long i) {
        this.id = i;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", deviceName=" + deviceName + ", operatingSystem=" + operatingSystem
                + ", searches=" + searches + ", uniqueIdentifier=" + uniqueIdentifier + "]";
    }

}