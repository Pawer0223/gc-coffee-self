package com.example.gccoffee.dao.products;

import com.example.gccoffee.dto.CreateProductDto;
import com.example.gccoffee.dto.UpdateProductDto;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.gccoffee.JdbcUtils.*;
import static com.example.gccoffee.dao.JdbcMessage.NO_INSERT;
import static com.example.gccoffee.dao.JdbcMessage.NO_UPDATE;
import static com.example.gccoffee.dao.products.ProductsField.*;


@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public Long insert(CreateProductDto createProductDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(
                "INSERT INTO products (product_name, category, price, description) VALUES (:productName, :category, :price, :description)",
                createProductDto.toParameterSource(),
                keyHolder,
                new String[] {PRODUCT_ID.getSnake()}
        );
        if (update != 1) {
            throw new RuntimeException(NO_INSERT.getMessage());
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(UpdateProductDto updateProductDto) {
        int update = jdbcTemplate.update(
                "UPDATE products SET product_name = :productName, category = :category, price = :price, description = :description" +
                        " WHERE product_id = :productId",
                updateProductDto.toParamMap()
        );
        if (update != 1) {
            throw new RuntimeException(NO_UPDATE.getMessage());
        }
    }

    @Override
    public Optional<Product> findById(Long productId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = :productId",
                            Collections.singletonMap(PRODUCT_ID.getCamel(), productId), productRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_name = :productName",
                            Collections.singletonMap(PRODUCT_NAME.getCamel(), productName), productRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "SELECT * FROM products WHERE category = :category",
                Collections.singletonMap(CATEGORY.getCamel(), category.toString()),
                productRowMapper
        );
    }

    private static final RowMapper<Product> productRowMapper = ((resultSet, i) -> {
        Long productId = resultSet.getLong(PRODUCT_ID.getSnake());
        String productName = resultSet.getString(PRODUCT_NAME.getSnake());
        Category category = Category.valueOf(resultSet.getString(CATEGORY.getSnake()));
        long price = resultSet.getLong(PRICE.getSnake());
        String description = resultSet.getString(DESCRIPTION.getSnake());
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp(CREATED_AT.getSnake()));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp(UPDATED_AT.getSnake()));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    });

    private Map<String, Object> toParamMap(Product product) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put(PRODUCT_ID.getCamel(), product.getProductId());
        paramMap.put(PRODUCT_NAME.getCamel(), product.getProductName());
        paramMap.put(CATEGORY.getCamel(), product.getCategory().toString());
        paramMap.put(PRICE.getCamel(), product.getPrice());
        paramMap.put(DESCRIPTION.getCamel(), product.getDescription());
        paramMap.put(CREATED_AT.getCamel(), product.getCreatedAt());
        paramMap.put(UPDATED_AT.getCamel(), product.getUpdatedAt());
        return paramMap;
    }
}
