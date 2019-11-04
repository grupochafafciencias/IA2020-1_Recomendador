package com.example.recomendador.service;

import com.example.recomendador.models.Series;

import java.util.List;

public interface SeriesService {
    void save(Series series);
    Series findByTitle(String title);
    List<Series> findAll();
}
