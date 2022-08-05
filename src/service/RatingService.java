package service;

import entity.Rating;

import java.util.List;

public interface RatingService {

    void setRating(Rating rating);

    int getAverageRating(String game);

    int getRating(String game, String userName);

    void reset();


}
