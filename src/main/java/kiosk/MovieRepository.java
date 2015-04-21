package kiosk;

import java.util.List;

public interface MovieRepository {

    MovieListing getMovies();

    void load(List<Movie> movies);

    void returnMovie(Movie movie);

    Movie getMovie(long id);

    List<Media> dispense(List<Movie> moviesInCart);
}
