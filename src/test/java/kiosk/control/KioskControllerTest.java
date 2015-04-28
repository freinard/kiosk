package kiosk.control;

import java.util.ArrayList;

import kiosk.Movie;
import kiosk.inventory.status.InventoryManager;
import kiosk.inventory.tracking.InventoryScanner;
import kiosk.media.MediaContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KioskControllerTest {

    @Mock private MediaContainer container;
    @Mock private InventoryScanner scanner;
    @Mock private InventoryManager manager;

    private KioskController controller;

    @Before
    public void setUp() {
        controller = new KioskController();
        controller.setInventoryManager(manager);
        controller.setInventoryScanner(scanner);
        controller.setMediaContainer(container);
    }

    @Test
    public void shouldSyncInventoryOfferingAndDispenserStoreAtStartUp() {
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movies.add(movie);
        when(scanner.scan(container)).thenReturn(movies);

        controller.start();

        ArrayList<Movie> expectedList = new ArrayList<>();
        expectedList.add(movie);
        verify(manager).initialize(expectedList);
    }

    @Test
    public void shouldShowStatusAsOnWhenStarted() {
        controller.start();

        assertTrue(controller.isOn());
    }

    @Test
    public void shouldShowStatusAsOffWhenStopped() {
        controller.start();

        controller.stop();

        assertFalse(controller.isOn());
    }

}