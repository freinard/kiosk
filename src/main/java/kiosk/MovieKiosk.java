package kiosk;

import java.util.List;

public interface MovieKiosk {

    MovieListing getMovies();

    MovieListing getMovies(MovieListingFilter filter);

    void load(List<Movie> movies);

    void returnMovie(Movie movie);

    void selectMovie(Movie movie);

    void deselectMovie(Movie movie);

    void startOver();

    List<Media> checkout(CreditCard card) throws CheckoutException;
}
