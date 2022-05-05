package com.example.gccoffee.dao;

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
    private final Product newProduct = new Product(0L, "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

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
        repository.insert(newProduct);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        Product newProduct2 = new Product(0L, "new-product2", Category.COFFEE_BEAN_PACKAGE, 2000L);
        repository.insert(newProduct2);
        List<Product> products = repository.findAll();
        assertAll(
                () -> assertThat(products.isEmpty()).isFalse(),
                () -> assertThat(products.size()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        Optional<Product> product = repository.findByName(newProduct.getProductName());

        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                () -> assertThat(product.get().getProductName()).isEqualTo(newProduct.getProductName())
        );
    }

    @Test
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        Long productId = repository.findAll().get(0).getProductId();
        Optional<Product> product = repository.findById(productId);

        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                 () -> assertThat(product.get().getProductId()).isEqualTo(productId)
        );
    }

    @Test
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        List<Product> products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        assertAll(
                () -> assertThat(products.isEmpty()).isFalse(),
                () -> assertThat(products.get(0).getCategory()).isEqualTo(newProduct.getCategory())
        );
    }

    @Test
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        String updateName = "updated-product";
        newProduct.setProductName(updateName);
        Long productId = repository.findAll().get(0).getProductId();
        repository.update(new Product(productId, updateName, Category.COFFEE_BEAN_PACKAGE, 2000L));

        Optional<Product> product = repository.findById(productId);
        assertAll(
                () -> assertThat(product.isEmpty()).isFalse(),
                () -> assertThat(product.get().getProductName()).isEqualTo(updateName)
        );
    }

    @Test
    @DisplayName("상품을 전체 삭제한다.")
    void testDeleteAll() {
        repository.deleteAll();
        List<Product> all = repository.findAll();

        assertThat(all.isEmpty()).isTrue();
    }
}