package kiosk.inventory.tracking;

import java.util.List;

import kiosk.archive.Movie;
import kiosk.media.MediaContainer;

public interface InventoryScanner {

    List<Movie> scan(MediaContainer dispenser);

}
