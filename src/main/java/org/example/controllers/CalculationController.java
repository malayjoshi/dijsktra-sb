package org.example.controllers;

import org.example.exceptions.GraphException;

import org.example.models.GraphDTO;
import org.example.models.ResponseDTO;

import org.example.services.DijsktraService;
import org.example.services.MaxflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/flows")
public class CalculationController {

    private final DijsktraService dijsktraService;
    private final MaxflowService maxflowService;

    public CalculationController(DijsktraService calculationService, MaxflowService maxflowService) {
        this.dijsktraService = calculationService;
        this.maxflowService = maxflowService;
    }



    @PostMapping("/calculate")
    private ResponseEntity<ResponseDTO> calculate(@RequestBody GraphDTO request) {
        if (request.getCalculationType().equals("dijsktra")) {
            try {
                return new ResponseEntity<>
                        (dijsktraService.calculate(request), HttpStatus.OK);
            } catch (GraphException e) {
                throw new RuntimeException(e);
            }
        }else if (request.getCalculationType().equals("maxflow")) {
            try {
                return new ResponseEntity<>
                        (maxflowService.calculate(request), HttpStatus.OK);
            } catch (GraphException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
