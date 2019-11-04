package com.example.recomendador.service;

import com.example.recomendador.conn.SeriesRepository;
import com.example.recomendador.models.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeriesServiceImplementation implements SeriesService{
    @Autowired
    private SeriesRepository seriesRepository;

    @Override
    public void save(Series series) {
        seriesRepository.save(series);
    }

    @Override
    public Series findByTitle(String title) {
        return seriesRepository.findByTitle(title);
    }
    public List<Series> findAll(){
        return toList(seriesRepository.findAll());
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }
}
