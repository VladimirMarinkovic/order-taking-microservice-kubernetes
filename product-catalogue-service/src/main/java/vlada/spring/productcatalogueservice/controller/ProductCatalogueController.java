package vlada.spring.productcatalogueservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.productcatalogueservice.dto.CatalogDto;
import vlada.spring.productcatalogueservice.dto.PackageDto;
import vlada.spring.productcatalogueservice.dto.ProductDto;
import vlada.spring.productcatalogueservice.service.CatalogService;
import vlada.spring.productcatalogueservice.service.PackageService;
import vlada.spring.productcatalogueservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class ProductCatalogueController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private CatalogService catalogService;


    @GetMapping("/test")
    public String test() { return "{test:true}"; }


    @GetMapping("/catalogue")
    public ResponseEntity<List<CatalogDto>> showAllProductsAndPackages() {
        return new ResponseEntity<>(catalogService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> showAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/packages")
    public ResponseEntity<List<PackageDto>> showAllPackages() {
        return new ResponseEntity<>(packageService.findAllPackages(), HttpStatus.OK);
    }




    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> findOneProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.findOneProduct(id), HttpStatus.OK);
    }

    @GetMapping("/productname/{name}")
    public ResponseEntity<ProductDto> findOneProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.findOneProductByName(name), HttpStatus.OK);
    }




    @GetMapping("/package/{id}")
    public ResponseEntity<PackageDto> findOnePackage(@PathVariable String id) {
        return new ResponseEntity<>(packageService.findOnePackage(id), HttpStatus.OK);
    }

    @GetMapping("/packagename/{name}")
    public ResponseEntity<PackageDto> findOnePackageByName(@PathVariable String name) {
        return new ResponseEntity<>(packageService.findOnePackageByName(name), HttpStatus.OK);
    }






}
