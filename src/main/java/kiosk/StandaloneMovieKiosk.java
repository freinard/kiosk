package kiosk;

import java.util.List;

public class StandaloneMovieKiosk implements MovieKiosk {

    private MovieRepository movieRepo;
    private MovieCart cart;
    private Cashier cashier;

    @Override
    public MovieListing getMovies() {
        return movieRepo.getMovies();
    }

    @Override
    public MovieListing getMovies(MovieListingFilter filter) {
        return filter.doFilter(movieRepo.getMovies());
    }

    @Override
    public void load(List<Movie> movies) {
        movieRepo.load(movies);
    }

    @Override
    public void returnMovie(Movie movie) {
        movieRepo.returnMovie(movie);
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
    public List<Media> checkout(CreditCard card) throws CheckoutException {
        chargeCard(card);
        List<Movie> movies = cart.getMovies();
        cashier.reset();
        cart.empty();
        return movieRepo.dispense(movies);
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
