package com.example.gccoffee.dao.orders;

import com.example.gccoffee.dto.CreateOrderDto;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.model.OrderStatus;
import com.example.gccoffee.model.vo.Email;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.gccoffee.JdbcUtils.toLocalDateTime;
import static com.example.gccoffee.dao.orders.OrdersField.*;
import static com.example.gccoffee.dao.products.ProductsField.CREATED_AT;
import static com.example.gccoffee.dao.products.ProductsField.UPDATED_AT;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private static final RowMapper<Order> productRowMapper = ((resultSet, i) -> {
        Long orderId = resultSet.getLong(ORDER_ID.getSnake());
        String email = resultSet.getString(EMAIL.getSnake());
        String address = resultSet.getString(ADDRESS.getSnake());
        String postcode = resultSet.getString(POSTCODE.getSnake());
        List<OrderItem> orderItems = Collections.emptyList();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(ORDER_STATUS.getSnake()));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp(CREATED_AT.getSnake()));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp(UPDATED_AT.getSnake()));
        return new Order(orderId, new Email(email), address, postcode, orderItems, orderStatus, createdAt, updatedAt);
    });
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Long insert(CreateOrderDto createOrderDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO orders(email, address, postcode, order_status) " +
                        "VALUES (:email, :address, :postcode, :orderStatus)",
                createOrderDto.toParameterSource(),
                keyHolder,
                new String[]{ORDER_ID.getSnake()}
        );
        Long orderId = keyHolder.getKey().longValue();
        createOrderDto.getOrderItems()
                .forEach(item ->
                        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, price, quantity) " +
                                        "VALUES (:orderId, :productId, :price, :quantity)",
                                toOrderItemParamMap(orderId, item)));
        return orderId;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = :orderId",
                            Collections.singletonMap(ORDER_ID.getCamel(), orderId), productRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Map<String, Object> toOrderParamMap(Order order) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put(ORDER_ID.getCamel(), order.getOrderId().toString().getBytes());
        paramMap.put(EMAIL.getCamel(), order.getEmail().getAddress());
        paramMap.put(ADDRESS.getCamel(), order.getAddress());
        paramMap.put(POSTCODE.getCamel(), order.getPostcode());
        paramMap.put(ORDER_STATUS.getCamel(), order.getOrderStatus().toString());
        paramMap.put(CREATED_AT.getCamel(), order.getCreatedAt());
        paramMap.put(UPDATED_AT.getCamel(), order.getUpdatedAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(Long orderId,
                                                    OrderItem item) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put(OrderItemsField.ORDER_ID.getCamel(), orderId);
        paramMap.put(OrderItemsField.PRODUCT_ID.getCamel(), item.getProductId());
        paramMap.put(OrderItemsField.PRICE.getCamel(), item.getPrice());
        paramMap.put(OrderItemsField.QUANTITY.getCamel(), item.getQuantity());
        return paramMap;
    }
}
