package com.example.gccoffee.controller;

import com.example.gccoffee.dto.CreateOrderDto;
import com.example.gccoffee.model.OrderItem;
import com.example.gccoffee.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderRestController.class)
@AutoConfigureMockMvc
class OrderRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("새로운 주문을 생성 할 수 있다.")
    void createNewOrder() throws Exception {
        List<OrderItem> orderItems = List.of(
                new OrderItem(1L, 100L, 1),
                new OrderItem(2L, 200L, 1),
                new OrderItem(3L, 300L, 3)
        );
        CreateOrderDto createOrderDto = new CreateOrderDto(
                "test@email.com",
                "집 주소",
                "123456",
                orderItems
        );
        given(orderService.createOrder(createOrderDto)).willReturn(1L);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderDto))
        )
                .andExpect(status().isCreated())
                .andDo(print())
        ;
    }
}