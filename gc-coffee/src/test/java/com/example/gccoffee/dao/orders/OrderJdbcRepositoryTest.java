package com.example.gccoffee.dao.orders;

import com.example.gccoffee.config.TestJdbcConfig;
import com.example.gccoffee.dao.products.ProductJdbcRepository;
import com.example.gccoffee.dao.products.ProductRepository;
import com.example.gccoffee.dto.CreateOrderDto;
import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {TestJdbcConfig.class, ProductJdbcRepository.class, OrderJdbcRepository.class})
@Transactional
@ActiveProfiles("test")
class OrderJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;
    List<OrderItem> orderItems = new ArrayList<>();

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-gc_coffee", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void init() {
        for (int i = 1; i <= 3; i++) {
            Long price = 100L * i;
            String name = price + "원 커피";
            CreateProductDto prdDto = new CreateProductDto(name, Category.COFFEE_BEAN_PACKAGE, price, name + "입니다.");
            Long productId = productRepository.insert(prdDto);
            orderItems.add(new OrderItem(productId, price, i));
        }
    }

    @AfterEach
    void clearAll() {
        orderItems = new ArrayList<>();
    }

    @Test
    @Description("새로운 주문을 추가할 수 있다.")
    void testInsert() {
        CreateOrderDto createOrderDto = new CreateOrderDto("user@email.com", "address", "123456", orderItems);
        Long insertedId = orderRepository.insert(createOrderDto);
        Optional<Order> order = orderRepository.findById(insertedId);

        assertAll(
                () -> assertThat(order.isEmpty()).isFalse(),
                () -> assertThat(order.get().getOrderId()).isEqualTo(insertedId)
        );
    }
}