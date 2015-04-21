package kiosk;

import java.util.List;

public interface MovieListing {

    List<Movie> getAvailableMovies();

    List<Movie> getUnavailableMovies();

}
