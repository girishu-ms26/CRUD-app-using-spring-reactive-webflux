package com.at0m.reactive.reactivecrud.service;

import com.at0m.reactive.reactivecrud.dto.ProductDto;
import com.at0m.reactive.reactivecrud.repo.ProductRepo;
import com.at0m.reactive.reactivecrud.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Flux<ProductDto> getProducts(){
        return productRepo.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return productRepo.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInPriceRange(double min,double max){
        return productRepo.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        return productDto.map(AppUtils::dtoToEntity)
                .flatMap(productRepo::insert)
                .map(AppUtils::entityToDto);
    }
    
    public Mono<ProductDto> updateProduct(Mono<ProductDto> ProductDtoMono,String id) {
        return productRepo.findById(id)
                .flatMap(p->ProductDtoMono.map(AppUtils::dtoToEntity)
                 .doOnNext(e->e.setId(id))
                 .flatMap(productRepo::save)
                 .map(AppUtils::entityToDto));
    }

    public Mono<Void> deleteProduct(String id){
        return productRepo.deleteById(id);
    }
}

