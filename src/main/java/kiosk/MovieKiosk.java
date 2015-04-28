package kiosk;

import java.util.List;

public interface MovieKiosk {

    MovieListing getMovies();

    MovieListing getMovies(MovieListingFilter filter);

    void stock(List<Movie> movies);
    // stock with Media not Movies, Media has a movie
    // give the movie to the scanner
    // give the disk to the dispenser

    void returnMovie(Movie movie);

    void selectMovie(Movie movie);

    void deselectMovie(Movie movie);

    void startOver();

    List<Media> checkout(CreditCard card) throws CheckoutException;
}
