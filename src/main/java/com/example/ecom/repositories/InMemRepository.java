package com.example.ecom.repositories;

import com.example.ecom.models.BaseModel;

import java.util.*;

public abstract class InMemRepository<T extends BaseModel> {
    protected Map<Integer, T> map = new HashMap<>();

    public T save(T t){
        if(t.getId()<=0){
            t.setId(map.keySet().stream().max(Comparator.naturalOrder()).orElse(0)+1);
        }
        map.put(t.getId(),t);
        return map.get(t.getId());
    }

    public Optional<T> findById(int id){
        return Optional.ofNullable(map.get(id));
    }

    public List<T> findAll(){
        return map.values().stream().toList();
    }
    public void deleteAll(){
        map.clear();
    }
}
