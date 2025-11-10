package com.sashafiesta.finances.repository.impl;

import com.sashafiesta.finances.repository.Repository;

import java.util.*;

public abstract class SimpleRepository<T> implements Repository<T> {
    private final Map<Long, T> dict = new HashMap<>();

    public interface Extractor<T> {
        long extract(T item);
    }
    private Extractor<T> idExtractor;


    public SimpleRepository(Extractor<T> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void save(T item) {
        dict.put(idExtractor.extract(item), item);
    }

    @Override
    public Optional<T> find(long id) {
        return Optional.ofNullable(dict.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(dict.values());
    }

    @Override
    public void delete(long id) {
        dict.remove(id);
    }

    @Override
    public void update(T item) throws IllegalArgumentException {
        long id = idExtractor.extract(item);
        if (dict.containsKey(id)) {
            dict.put(id, item);
        } else {
            throw new IllegalArgumentException("item not found");
        }
    }
}
