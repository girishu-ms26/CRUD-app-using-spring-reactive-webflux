package com.at0m.reactive.reactivecrud.repo;

import com.at0m.reactive.reactivecrud.dto.ProductDto;
import com.at0m.reactive.reactivecrud.model.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepo extends ReactiveMongoRepository<Product,String> {
    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);
}
