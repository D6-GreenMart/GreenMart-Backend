package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.OrderDto;
import com.greenmart.app.domain.entities.Order;
import com.greenmart.app.domain.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-11T08:49:34+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.userId( orderUserId( order ) );
        orderDto.orderItems( mapOrderItems( order.getOrderItems() ) );
        orderDto.address( order.getAddress() );
        orderDto.amount( order.getAmount() );
        orderDto.createdAt( order.getCreatedAt() );
        orderDto.id( order.getId() );
        orderDto.status( order.getStatus() );

        return orderDto.build();
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDto( order ) );
        }

        return list;
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.user( orderDtoToUser( orderDto ) );
        order.address( orderDto.getAddress() );
        order.amount( orderDto.getAmount() );
        order.createdAt( orderDto.getCreatedAt() );
        order.id( orderDto.getId() );
        order.status( orderDto.getStatus() );

        return order.build();
    }

    private UUID orderUserId(Order order) {
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    protected User orderDtoToUser(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( orderDto.getUserId() );

        return user.build();
    }
}
