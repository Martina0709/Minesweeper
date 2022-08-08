package service;

//CREATE TABLE rating(
//        game VARCHAR(64) NOT NULL,
//        username VARCHAR(64) NOT NULL,
//        rating int NOT NULL CHECK (rating>0 AND rating<6),
//        rated_on TIMESTAMP NOT NULL,
//        UNIQUE(game, username)
//        );

import entity.Rating;


import java.util.List;

public interface RatingService {

    void setRating(Rating rating);

    int getAverageRating(String game);

    int getRating(String game, String userName);

    void reset();


}
