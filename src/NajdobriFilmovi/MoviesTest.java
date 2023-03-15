package NajdobriFilmovi;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

class Movie{
    private String title;
    private int [] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public int getRatingsSize(){
        return ratings.length;
    }

    public double getAvgRatings(){
        return Arrays.stream(ratings).mapToDouble(i -> i).average().orElse(0);
    }

    public double getCoef(){
        //просечен ретјтинг на филмот x вкупно број на рејтинзи на филмот / максимален број на рејтинзи (од сите филмови во листата)
        return getAvgRatings() * getRatingsSize();
    }

    public String getTitle(){
        return title;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, getAvgRatings(), getRatingsSize());
    }
}

class MoviesList{
    public List<Movie> movies;

    public MoviesList() {
        movies = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings){
        movies.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating(){
        Comparator<Movie> comparator = Comparator.comparing(Movie::getAvgRatings).reversed().thenComparing(Movie::getTitle);
        return movies.stream().sorted(comparator).limit(10).collect(Collectors.toList());
    }

    public List<Movie> top10ByRatingCoef(){
        Comparator<Movie> comparator = Comparator.comparing(Movie::getCoef).reversed().thenComparing(Movie::getTitle);
        return movies.stream().sorted(comparator).limit(10).collect(Collectors.toList());
    }
}