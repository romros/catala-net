package cat.fundacio.catalanet.scheduler.util;

import org.springframework.stereotype.Component;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@Component
public class DeviceUtils {

    private final CentralProcessor processor;
    private long[] prevTicks;

    public DeviceUtils(SystemInfo systemInfo) {
        this.processor = systemInfo.getHardware().getProcessor();
        this.prevTicks = processor.getSystemCpuLoadTicks();
    }

    public double cpuLoad() {
        // Espera un moment per obtenir estadístiques més precises de la càrrega
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks);
        prevTicks = processor.getSystemCpuLoadTicks();

        return cpuLoad;
    }
}