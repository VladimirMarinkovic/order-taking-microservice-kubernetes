package vlada.spring.ordertakingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.PackageDto;
import vlada.spring.ordertakingservice.dto.ProductDto;
import vlada.spring.ordertakingservice.model.Package;
import vlada.spring.ordertakingservice.model.Product;
import vlada.spring.ordertakingservice.service.OrderInfoService;


@RestController
@RequestMapping("/orderinfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>(orderInfoService.test(), HttpStatus.OK);
    }


    @GetMapping("/product/{name}")
    public ResponseEntity<ProductDto> getSelectedProduct(@PathVariable("name") String name) {
        return new ResponseEntity<>(orderInfoService.getSelectedProduct(name), HttpStatus.OK);
    }

    @GetMapping("/package/{name}")
    public ResponseEntity<PackageDto> getSelectedPackage(@PathVariable("name") String name) {
        return new ResponseEntity<>(orderInfoService.getSelectedPackage(name), HttpStatus.OK);
    }



























}
