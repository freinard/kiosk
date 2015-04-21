package kiosk;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StandaloneMovieKioskTest {

    @Mock private MovieRepository movieRepo;
    @Mock private MovieListing expectedListing;
    @Mock private MovieCart cart;
    @Mock private Cashier cashier;

    private StandaloneMovieKiosk kiosk;

    @Before
    public void setUp() {
        when(movieRepo.getMovies()).thenReturn(expectedListing);

        kiosk = new StandaloneMovieKiosk();
        kiosk.setMovieRepository(movieRepo);
        kiosk.setCart(cart);
        kiosk.setCashier(cashier);
    }

    @Test
    public void shouldReturnListOfAllMovies() {
        MovieListing listing = kiosk.getMovies();

        assertEquals(expectedListing, listing);
    }

    @Test
    public void shouldApplyFilterToMovieListing() {
        MovieListing filteredListing = mock(MovieListing.class);
        MovieListingFilter filter = mock(MovieListingFilter.class);
        when(filter.doFilter(expectedListing)).thenReturn(filteredListing);

        MovieListing listing = kiosk.getMovies(filter);

        assertEquals(filteredListing, listing);
    }

    @Test
    public void shouldStoreLoadedMoviesForListing() {
        List<Movie> movies = new ArrayList<>();

        kiosk.load(movies);

        verify(movieRepo).load(movies);
    }

    @Test
    public void shouldStoreReturnedMovieForListing() {
        Movie movie = mock(Movie.class);

        kiosk.returnMovie(movie);

        verify(movieRepo).returnMovie(movie);
    }

    @Test
    public void shouldAddSelectedMovieToCart() {
        Movie movie = mock(Movie.class);

        kiosk.selectMovie(movie);

        verify(cart).add(movie);
    }

    @Test
    public void shouldRemoveSelectedMovieFromCart() {
        Movie movie = mock(Movie.class);

        kiosk.deselectMovie(movie);

        verify(cart).remove(movie);
    }

    @Test
    public void shouldClearCartWhenStartingOver() {
        kiosk.startOver();

        verify(cart).empty();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldChargeCardDispenseMoviesClearCartAndPaymentAtCheckout() throws Exception {
        CreditCard card = mock(CreditCard.class);
        Movie movie = mock(Movie.class);
        CartFake cart = new CartFake();
        cart.add(movie);
        kiosk.setCart(cart);
        List<Media> discs = mock(List.class);
        when(movieRepo.dispense(cart.getMovies())).thenReturn(discs);

        List<Media> rented = kiosk.checkout(card);

        InOrder inOrder = inOrder(cashier);
        inOrder.verify(cashier).charge(card);
        inOrder.verify(cashier).reset();
        assertEquals(discs, rented);
        assertEquals(0, cart.getMovies().size());
    }

    @Test
    public void shouldNotDispenseIfInsufficientFunds() throws InsufficientFundsException {
        CreditCard card = mock(CreditCard.class);
        InsufficientFundsException fundsException = new InsufficientFundsException();
        doThrow(fundsException).when(cashier).charge(card);
        try {
            kiosk.checkout(card);
        } catch (CheckoutException e) {
            assertEquals("Unable to charge card!", e.getMessage());
            assertEquals(fundsException, e.getCause());
        }
    }

}
