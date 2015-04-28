package kiosk.control;

import kiosk.inventory.status.InventoryManager;
import kiosk.inventory.tracking.InventoryScanner;
import kiosk.media.MediaContainer;

public class KioskController {

    private InventoryManager inventoryManager;
    private InventoryScanner inventoryScanner;
    private MediaContainer mediaContainer;
    private KioskStatus status;

    public void start() {
        inventoryManager.initialize(inventoryScanner.scan(mediaContainer));
        status = KioskStatus.ON;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void setInventoryScanner(InventoryScanner inventoryScanner) {
        this.inventoryScanner = inventoryScanner;
    }

    public void setMediaContainer(MediaContainer mediaContainer) {
        this.mediaContainer = mediaContainer;
    }

    public boolean isOn() {
        return KioskStatus.ON.equals(status);
    }

    public void stop() {
        status = KioskStatus.OFF;
    }
}
