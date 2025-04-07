package com.moviedetials.cineWorld.connection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")

public class MovieDetails {

    private final String url = "jdbc:mysql://localhost:3306/movie";
    private final String username = "root";
    private final String password = "root";

    @GetMapping

    public List<HashMap<String, Object>> getAllMovies() throws SQLException {
        List<HashMap<String, Object>> moviesDetailsList = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement();
             ResultSet re = st.executeQuery(query)) {

            System.out.println("Connection Established successfully");

            while (re.next()) {
                moviesDetailsList.add(mapMovieDetails(re));
            }
        }
        return moviesDetailsList;
    }
    @GetMapping("/{id}")

    public ResponseEntity<HashMap<String, Object>> getMovieById(@PathVariable int id) throws SQLException {
        String query = "SELECT * FROM movies WHERE id = ?";


        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);
            try (ResultSet re = pst.executeQuery()) {
                if (re.next()) {

                    return ResponseEntity.ok(mapMovieDetails(re));
                }
                else {

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            }
        }
    }


    private HashMap<String, Object> mapMovieDetails(ResultSet re) throws SQLException {

        HashMap<String, Object> moviedetails = new HashMap<>();
        moviedetails.put("id", re.getInt("id"));
        moviedetails.put("movie_name", re.getString("movie_name"));
        moviedetails.put("release_date", re.getDate("release_date"));
        moviedetails.put("trailer_url", re.getString("trailer_url"));
        moviedetails.put("cast", re.getString("cast"));
        moviedetails.put("img_url", re.getString("img_url"));
        moviedetails.put("director_name", re.getString("director"));
        moviedetails.put("description", re.getString("description"));
        moviedetails.put("rating", re.getString("rating"));
        moviedetails.put("genre", re.getString("genre"));
        return moviedetails;
    }

    @PostMapping("/movies")

    public ResponseEntity<String> addMovieDetails(@RequestBody Map<String, Object> movieDetailsData) {
        // Note: We are not inserting the id as it is auto-increment
        String query = "INSERT INTO movies (id, moviename, releasedate, Trailerurl, TopCastlead, Topleadimg, subname, subimg, Director, MovieDescription) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, (Integer) movieDetailsData.get("id"));

            pst.setString(2, (String) movieDetailsData.get("moviename"));

            String releaseDateStr = (String) movieDetailsData.get("releasedate");
            java.sql.Date releasedate = java.sql.Date.valueOf(releaseDateStr);
            pst.setDate(3, releasedate);

            pst.setString(4, (String) movieDetailsData.get("Trailerurl"));
            pst.setString(5, (String) movieDetailsData.get("TopCastlead"));
            pst.setString(6, (String) movieDetailsData.get("Topleadimg"));
            pst.setString(7, (String) movieDetailsData.get("subname"));
            pst.setString(8, (String) movieDetailsData.get("subimg"));
            pst.setString(9, (String) movieDetailsData.get("Director"));
            pst.setString(10, (String) movieDetailsData.get("MovieDescription"));

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected);
            if (rowsAffected > 0) {

                return ResponseEntity.status(HttpStatus.CREATED).body("Movie details added successfully");
            }

            else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add movie details");
            }

        }

        catch (SQLException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        }
    }

}
