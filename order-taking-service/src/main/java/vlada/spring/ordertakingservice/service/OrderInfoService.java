package vlada.spring.ordertakingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import vlada.spring.ordertakingservice.dto.*;
import vlada.spring.ordertakingservice.feignclient.ProductCatalogFeignClient;
import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class OrderInfoService {


    @Autowired
    private ProductCatalogFeignClient productCatalogFeignClient;


    public String test() {
        return productCatalogFeignClient.test();
    }

    public ProductDto getSelectedProduct(String name) { return productCatalogFeignClient.getSelectedProduct(name); }

    public PackageDto getSelectedPackage(String name) { return productCatalogFeignClient.getSelectedPackage(name); }




    @Cacheable(value = "chosenProductsAndPackages")
    @Transactional
    public List<ProductDto> getChosenProductsAndPackagesByName(OrderRequest orderRequest) {

        StopWatch stopWatch = new StopWatch(); stopWatch.start();
        List<ProductDto> products = new ArrayList<>();
            for (ProductDto r : orderRequest.getProducts()) {
                ProductDto pDto = getSelectedProduct(r.getProductName());
                PackageDto paDto = getSelectedPackage(r.getChosenPackage().getPackageName());
                ProductDto product = ProductDto.builder()
                        .productName(pDto.getProductName())
                        .productId(pDto.getProductId())
                        .chosenPackage(paDto)
                        .build();
                products.add(product);
            }
        stopWatch.stop();
        log.info("Get chosen products in seconds >> {} :  " + stopWatch.getTotalTimeSeconds());
        return products;

    }



}
