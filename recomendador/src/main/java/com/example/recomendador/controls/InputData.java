package com.example.recomendador.controls;

import com.example.recomendador.models.Series;
import com.example.recomendador.models.User;
import com.example.recomendador.models.UserSeries;

import java.util.*;
public class InputData {
    protected static List<Series> items;
    public static Map<User, HashMap<Series, Double>> initializeData(List<User>users, List<Series> series) {
        items=series;
        Map<User, HashMap<Series, Double>> data = new HashMap<>();
        HashMap<Series, Double> newUser;
        Set<UserSeries> newRecommendationSet;
        for (User e:users) {
            newUser = new HashMap<Series, Double>();
            newRecommendationSet = new HashSet<>();
            for (UserSeries vista:e.getWatched()) {
                newRecommendationSet.add(vista);
            }
            for (UserSeries item : newRecommendationSet) {
                newUser.put(item.getSeries(),item.getRating());
            }
            data.put(e,newUser);
        }
        return data;
    }
}
