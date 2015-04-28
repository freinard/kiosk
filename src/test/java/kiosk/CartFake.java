package kiosk;

import java.util.ArrayList;
import java.util.List;

import kiosk.archive.Movie;
import kiosk.archive.MovieCart;

public class CartFake implements MovieCart {

    private List<Movie> movies;

    public CartFake() {
        movies = new ArrayList<>();
    }

    @Override
    public void add(Movie movie) {
        movies.add(movie);
    }

    @Override
    public void remove(Movie movie) {
        movies.remove(movie);
    }

    @Override
    public void empty() {
        movies.clear();
    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }
}
