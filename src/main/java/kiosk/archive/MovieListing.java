package kiosk.archive;

import java.util.List;

public interface MovieListing {

    List<Movie> getAvailableMovies();

    List<Movie> getUnavailableMovies();

}
