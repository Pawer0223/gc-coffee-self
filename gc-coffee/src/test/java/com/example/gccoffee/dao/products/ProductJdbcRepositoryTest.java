package com.example.gccoffee.dao.products;

import com.example.gccoffee.config.TestJdbcConfig;
import com.example.gccoffee.dao.products.ProductJdbcRepository;
import com.example.gccoffee.dao.products.ProductRepository;
import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.dto.UpdateProductDto;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = {TestJdbcConfig.class, ProductJdbcRepository.class})
@Transactional
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;
    private final CreateProductDto createProductDto = new CreateProductDto( "init-product", Category.COFFEE_BEAN_PACKAGE, 1000L, "DESC");
    private Long initInsertedId = 1L;

    @Autowired
    ProductRepository repository;

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
        initInsertedId = repository.insert(createProductDto);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        CreateProductDto createProductDto2 = new CreateProductDto( "new-product2", Category.COFFEE_BEAN_PACKAGE, 1000L, "DESC2");
        Long insertedId = repository.insert(createProductDto2);

        Optional<Product> product = repository.findById(insertedId);
        assertThat(product.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("상품을 전체 조회할 수 있다.")
    void testFindAll() {
        for (int i = 1; i <= 5; i++) {
            String name = "new-product" + i;
            CreateProductDto newProduct = new CreateProductDto( "new-product2", Category.COFFEE_BEAN_PACKAGE, 1000L, "DESC");
            repository.insert(newProduct);
        }

        List<Product> products = repository.findAll();
        assertAll(
                () -> assertThat(products.isEmpty()).isFalse(),
                () -> assertThat(products.size()).isEqualTo(6)
        );
    }

    @Test
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        Optional<Product> product = repository.findByName(createProductDto.getProductName());

        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                () -> assertThat(product.get().getProductName()).isEqualTo(createProductDto.getProductName())
        );
    }

    @Test
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        Optional<Product> product = repository.findById(initInsertedId);

        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                () -> assertThat(product.get().getProductId()).isEqualTo(initInsertedId)
        );
    }

    @Test
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        List<Product> products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        assertAll(
                () -> assertThat(products.isEmpty()).isFalse(),
                () -> assertThat(products.get(0).getCategory()).isEqualTo(createProductDto.getCategory())
        );
    }

    @Test
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        String updateName = "updated-product";
        createProductDto.setProductName(updateName);
        UpdateProductDto updateProductDto = new UpdateProductDto(
                initInsertedId,
                updateName,
                createProductDto.getCategory(),
                createProductDto.getPrice(),
                createProductDto.getDescription());
        repository.update(updateProductDto);

        Optional<Product> product = repository.findById(initInsertedId);
        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                () -> assertThat(product.get().getProductName()).isEqualTo(updateName)
        );
    }
}