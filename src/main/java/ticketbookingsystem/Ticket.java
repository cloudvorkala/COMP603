/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketbookingsystem;

/**
 *
 * @author Yzhang & cloud
 */
public class Ticket {
    private MovieShow movieShow;
    private String seatNumber;
    private double price;

    public Ticket(MovieShow movieShow, String seatNumber, double price) {
        this.movieShow = movieShow;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public MovieShow getMovieShow() {
        return movieShow;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket for " + movieShow.getMovieName() + " on " + movieShow.getDate() + " at " + movieShow.getTime() +
               ", Seat: " + seatNumber + ", Price: $" + price;
    }
}

