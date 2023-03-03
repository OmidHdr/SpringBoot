package com.example.springboot.mapper;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.customer.dtoCustomer;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.dto.login.Login;
import com.example.springboot.dto.offer.OfferDto;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.task.TaskDto;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Offers dtoToOffers(OffersSet dto);

    List<OffersSet> offerToDtoList(List<Offers> offers);

    OrderShow orderTodto(Orders orders);

    List<OrderShow> listOrdersToDto(List<Orders> offers);

    Opinion dtoToOpinion(DtoOpinion dto);

    Orders orderSaveToOrder(OrderSave orderSave);

    dtoCustomer customerToDto(Customer customer);
    dtoExpert expertToDto(Expert expert);
}
