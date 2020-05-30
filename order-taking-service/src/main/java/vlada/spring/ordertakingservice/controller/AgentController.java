package vlada.spring.ordertakingservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.OrderDto;
import vlada.spring.ordertakingservice.dto.OrderRequest;
import vlada.spring.ordertakingservice.dto.ProcessedOrderDto;
import vlada.spring.ordertakingservice.dto.ProcessingResponse;
import vlada.spring.ordertakingservice.model.ProcessedOrder;
import vlada.spring.ordertakingservice.service.ProcessedOrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agent/processedorder")
public class AgentController {

    @Autowired
    private ProcessedOrderService processedOrderService;


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/allwithcustomerdetails")
    public ResponseEntity<List<OrderDto>> showAllProcessedOrdersWithCustomerDetails() {
        return new ResponseEntity<>(processedOrderService.findAllProcessedOrdersWithCustomerDetails(), HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/all")
    public ResponseEntity<List<ProcessedOrderDto>> showAllProcessedOrders() {
        return new ResponseEntity<>(processedOrderService.findAllProcessedOrders(), HttpStatus.OK);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/contractnumber/{contractNumber}")
    public ResponseEntity<List<ProcessedOrderDto>> findProcessedOrderByCustomerContractNumber(@PathVariable (value = "contractNumber") String contractNumber) {
        return new ResponseEntity<>(processedOrderService.findProcessedOrderByCustomerContractNumber(contractNumber), HttpStatus.OK);
    }



    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/{id}")
    public ResponseEntity<ProcessedOrderDto> findProcessedOrderById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(processedOrderService.findProcessedOrderById(id), HttpStatus.OK);
    }



    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/edit/{id}")
    public ResponseEntity<ProcessingResponse> editProcessedOrder(
            @PathVariable(value = "id") String id, @Valid @RequestBody OrderRequest orderRequest) {
        processedOrderService.editProcessedOrder(id, orderRequest);
        return new ResponseEntity<>(new ProcessingResponse(200, "Order edited successfully!"), HttpStatus.OK);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/cancle/{id}")
    public ResponseEntity<ProcessingResponse> cancleProcessedOrder(@PathVariable(value = "id") String id) {
        processedOrderService.cancleProcessedOrder(id);
        return new ResponseEntity<>(new ProcessingResponse(200, "Order canceled successfully!"), HttpStatus.OK);
    }

}
