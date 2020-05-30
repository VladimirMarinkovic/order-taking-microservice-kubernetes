package vlada.spring.ordertakingservice.feignclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vlada.spring.ordertakingservice.dto.PackageDto;
import vlada.spring.ordertakingservice.dto.ProductDto;
import vlada.spring.ordertakingservice.model.Package;
import vlada.spring.ordertakingservice.model.Product;



@FeignClient(name = "product-catalogue-service", fallback = ProductCatalogFallback.class)
public interface ProductCatalogFeignClient {

    @GetMapping(value = "/offer/test")
    String test();


    @GetMapping("/offer/productname/{name}")
    ProductDto getSelectedProduct(@PathVariable("name") String name);

    @GetMapping("/offer/packagename/{name}")
    PackageDto getSelectedPackage(@PathVariable("name") String name);



}



@Component
class ProductCatalogFallback implements ProductCatalogFeignClient {

    @Override
    public String test() {
        return "Test Fail!";
    }

    @Override
    public ProductDto getSelectedProduct(String name) {
        return ProductDto.builder()
                .productName(name + ": product information not available at the moment.")
                .build();
    }

    @Override
    public PackageDto getSelectedPackage(String name) {
        return PackageDto.builder()
                .packageName(name + ": package information not available at the moment.")
                .build();
    }




}