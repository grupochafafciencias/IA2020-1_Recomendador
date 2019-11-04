package com.example.recomendador.models;

public class UserSeries {
    private Series series;
    private int rating;
    public UserSeries(){
    }
    public UserSeries(Series series,int rating){
        this.series=series;
        this.rating=rating;
    }

    public int getRating() {
        return rating;
    }

    public Series getSeries() {
        return series;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
