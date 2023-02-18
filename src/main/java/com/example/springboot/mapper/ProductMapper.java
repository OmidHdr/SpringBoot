package com.example.springboot.mapper;

import com.example.springboot.dto.TaskDto;
import com.example.springboot.entity.Tasks;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    //section task
    TaskDto taskToDto(Tasks tasks);
    Tasks dtoToTask(TaskDto taskDto);

}
