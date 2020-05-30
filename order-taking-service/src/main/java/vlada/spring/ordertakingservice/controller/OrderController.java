package vlada.spring.ordertakingservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.*;
import vlada.spring.ordertakingservice.service.OrderService;
import vlada.spring.ordertakingservice.service.PersonalDetailsService;

import javax.validation.Valid;



@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    PersonalDetailsService personalDetailsService;


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/test")
    public String test() {
        return "{test:true}";
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/ordertest")
    public ResponseEntity<OrderDto> processOrderTest(@Valid @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.mapOrderRequestToOrderDto(orderRequest), HttpStatus.OK);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/processorder")
    public ResponseEntity<ProcessingResponse> processOrder(@Valid @RequestBody OrderRequest orderRequest) {
        orderService.processOrder(orderService.mapOrderRequestToOrderDto(orderRequest));
        return new ResponseEntity<>(new ProcessingResponse(200, "Order proccess successfully!"), HttpStatus.OK);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/processordernewcustomer")
    public ResponseEntity<ProcessingResponse> processOrderForNewCustomer(@Valid @RequestBody OrderRequestNewCustomer orderRequestNewCustomer) {
        personalDetailsService.processCustomer(orderRequestNewCustomer.getCustomerDto());
        orderService.processOrder(orderService.mapOrderRequestNewCustomerToOrderDto(orderRequestNewCustomer));
        return new ResponseEntity<>(new ProcessingResponse(200, "Order proccess successfully!"), HttpStatus.OK);
    }




}
