package com.daniel.kshopee.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapData {



    private static ModelMapper modelMapper;

    @Autowired
    public MapData(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public static Object MapToDo(Object entityClass,Class dtoClass){
        Object obj = modelMapper.map(entityClass,dtoClass);
        return obj;
    }
}
