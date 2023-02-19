package com.example.springboot.mapper;

import com.example.springboot.dto.task.TaskDto;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.entity.Tasks;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    //section task
    TaskDto taskToDto(Tasks tasks);
    Tasks dtoToTask(TaskDto taskDto);

    OffersSet offerToDto(Offers offers);

    Offers dtoToOffers(OffersSet dto);

    List<Offers> dtoToOffersList(List<OffersSet> dtos);

    List<OffersSet> offerToDtoList(List<Offers> offers);

    OrderShow orderTodto(Orders orders);

    Orders dtoToOrders(OrderShow show);

}
