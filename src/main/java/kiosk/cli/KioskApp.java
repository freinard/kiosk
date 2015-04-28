package kiosk.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kiosk.control.KioskController;
import kiosk.inventory.status.InMemoryInventoryManager;
import kiosk.inventory.status.InventoryManager;
import kiosk.inventory.tracking.InventoryScanner;
import kiosk.inventory.tracking.InventoryScannerImpl;
import kiosk.media.InMemoryMediaContainer;
import kiosk.media.MediaContainer;

public class KioskApp {

    public static void main(String[] args) {
        KioskController controller = createController();
        try {
            controller.start();
            message("Kiosk has started.");
            message("Enter Q to shutdown kiosk.");
        } catch (Exception e) {
            e.printStackTrace();
            message("Unable to start kiosk due to error!");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;
        try {
            while (controller.isOn()) {
                command = br.readLine();
                message(handleCommand(command, controller));
            }
        } catch (IOException ioe) {
            message("Error reading command, please try again.");
            controller.stop();
        }

    }

    private static String handleCommand(String command, KioskController controller) {
        if ("q".equalsIgnoreCase(command)) {
            controller.stop();
            return "Kiosk is now off.";
        }
        return new DefaultCommandList().getMessage();
    }

    private static KioskController createController() {
        KioskController controller = new KioskController();
        InventoryManager manager = new InMemoryInventoryManager();
        InventoryScanner scanner = new InventoryScannerImpl();
        MediaContainer container = new InMemoryMediaContainer();
        controller.setInventoryManager(manager);
        controller.setInventoryScanner(scanner);
        controller.setMediaContainer(container);
        return controller;
    }

    private static void message(String s) {
        System.out.println(s);
    }

}
