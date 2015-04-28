package kiosk.cli;

public class DefaultCommandList extends AvailableCommandList {

    public DefaultCommandList() {
        availableCommands.put("q", "Will shut down the kiosk.");
        availableCommands.put("list", "Will list available movies.");
    }

}
