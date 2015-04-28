package kiosk.cli;

import java.util.HashMap;
import java.util.Map;

public class AvailableCommandList {

    protected Map<String, String> availableCommands = new HashMap<String, String>();

    public String getMessage() {
        String result = "Available commands are:\n";
        for (String cmd : availableCommands.keySet()) {
            result += cmd + " -> " + availableCommands.get(cmd) + "\n";
        }
        return result;
    }

}
