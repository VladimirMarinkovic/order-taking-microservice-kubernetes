package vlada.spring.productcatalogueservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlada.spring.productcatalogueservice.dto.ProductDto;
import vlada.spring.productcatalogueservice.model.Product;
import vlada.spring.productcatalogueservice.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    PackageService packageService;

    public List<ProductDto> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .map(this::mapProductToProductDto)
                .collect(Collectors.toList());
    }


    public ProductDto mapProductToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId().toString())
                .productName(product.getProductName())
                .build();
    }

    public ProductDto findOneProduct(String id) {
        Optional<Product> optionalProduct = productRepository.findById(Long.parseLong(id));
        Product product = optionalProduct.orElseThrow(IllegalArgumentException::new);
        return mapProductToProductDto(product);
    }


    public ProductDto findOneProductByName(String name) {
        Optional<Product> optionalProduct = productRepository.findByProductName(name);
        Product product = optionalProduct.orElseThrow(IllegalArgumentException::new);
        return mapProductToProductDto(product);
    }





}
