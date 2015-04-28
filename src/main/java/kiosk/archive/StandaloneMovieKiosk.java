package kiosk.archive;

import java.util.ArrayList;
import java.util.List;

public class StandaloneMovieKiosk implements MovieKiosk {

    private MovieRepository movieRepo;
    private MovieCart cart;
    private Cashier cashier;

    // manage the length of time a movie is checked out


    @Override
    public MovieListing getMovies() {
        // inventory.whatsAvailable()
        // inventory.whatIsUnavailable();
        return movieRepo.getMovies();
    }

    @Override
    public MovieListing getMovies(MovieListingFilter filter) {
        return filter.doFilter(movieRepo.getMovies());
    }

    @Override
    public void stock(List<Movie> movies) {
        movieRepo.load(movies);
        // dispenser.setMedia(List<Media>);
        // re-inventory
    }

    @Override
    public void returnMovie(Movie movie) {
        movieRepo.returnMovie(movie);
        // cashier.removeAccountPayableFor(movie);
    }

    @Override
    public void selectMovie(Movie movie) {
        cart.add(movie);
    }

    @Override
    public void deselectMovie(Movie movie) {
        cart.remove(movie);
    }

    @Override
    public void startOver() {
        cart.empty();
    }

    @Override
    // Return Receipt
    public List<Media> checkout(CreditCard card) throws CheckoutException {
        // get approval for card
        // dispense
        // charge card for dispensed movies
        // release the hold
        // update inventory
        // gen receipt

        // cashier setup accounts payable

        chargeCard(card);
        List<Movie> movies = cart.getMovies();
        cashier.reset();
        cart.empty();

        //List<Media> dispensed = successful(dispenser.dispense(movies));
        //inventory.decrementCount(movies);
        return new ArrayList<>(movieRepo.dispense(movies));
    }

    private void chargeCard(CreditCard card) throws CheckoutException {
        try {
            cashier.charge(card);
        } catch (InsufficientFundsException e) {
            throw new CheckoutException("Unable to charge card!", e);
        }
    }

    public void setCart(MovieCart cart) {
        this.cart = cart;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void setMovieRepository(MovieRepository repo) {
        movieRepo = repo;
    }

}
