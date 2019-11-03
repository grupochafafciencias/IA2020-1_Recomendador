package com.example.recomendador.conn;

import com.example.recomendador.models.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<Series,Integer> {
    Series findByTitle(String title);
}
