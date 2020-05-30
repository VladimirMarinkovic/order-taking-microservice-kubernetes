package vlada.spring.productcatalogueservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlada.spring.productcatalogueservice.dto.CatalogDto;
import vlada.spring.productcatalogueservice.model.Product;
import vlada.spring.productcatalogueservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    PackageService packageService;


    public List<CatalogDto> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .map(this::mapProductToProductDto)
                .collect(Collectors.toList());
    }

    public CatalogDto mapProductToProductDto(Product product) {
        return CatalogDto.builder()
                .productId(product.getId().toString())
                .productName(product.getProductName())
                .packages(product.getPackageList().stream()
                        .map(p -> packageService.mapPackageToPackageDto(p))
                        .collect(Collectors.toList())
                )
                .build();
    }








}
