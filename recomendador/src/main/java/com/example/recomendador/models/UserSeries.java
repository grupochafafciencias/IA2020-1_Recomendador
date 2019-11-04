package com.example.recomendador.models;

import java.io.Serializable;

public class UserSeries implements Serializable {
    private Series series;
    private Double rating;
    public UserSeries(){
    }
    public UserSeries(Series series,Double rating){
        this.series=series;
        this.rating=rating;
    }

    public Double getRating() {
        return rating;
    }

    public Series getSeries() {
        return series;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return (series+" , "+rating);
    }
}
