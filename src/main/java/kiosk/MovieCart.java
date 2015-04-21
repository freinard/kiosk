package kiosk;

import java.util.List;

public interface MovieCart {

    void add(Movie movie);

    void remove(Movie movie);

    void empty();

    List<Movie> getMovies();
}
