

/**
 *
 * @author Yzhang & cloud
 */
package ticketbookingsystem;

import java.util.ArrayList;
import java.util.List;

public class ShowManager {
    private List<MovieShow> movieShows = new ArrayList<>();

    public void addMovieShow(MovieShow movieShow) {
        movieShows.add(movieShow);
    }

    public List<MovieShow> getAvailableMovieShows() {
        return movieShows;
    }

    // Select a movie show by its unique ID
    public MovieShow selectMovieShowById(int showId) {
        for (MovieShow movieShow : movieShows) {
            if (movieShow.getShowId() == showId) {
                return movieShow;
            }
        }
        return null;  // Return null if no show is found with the given ID
    }
}

