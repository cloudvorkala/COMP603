/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketbookingsystem;

/**
 *
 * @author Yzhang & cloud
 */
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

    public MovieShow selectMovieShow(String movieName, String date, String time) {
        for (MovieShow movieShow : movieShows) {
            if (movieShow.getMovieName().equalsIgnoreCase(movieName) &&
                movieShow.getDate().equals(date) &&
                movieShow.getTime().equals(time)) {
                return movieShow;
            }
        }
        return null;
    }
}

