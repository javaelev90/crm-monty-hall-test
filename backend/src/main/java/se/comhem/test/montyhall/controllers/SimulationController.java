package se.comhem.test.montyhall.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import se.comhem.test.montyhall.model.MontyHallSimulationResult;
import se.comhem.test.montyhall.services.ISimulationService;

import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;


@RestController
@RequestMapping("simulation")
@Validated
public class SimulationController {

    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    private final ISimulationService simulationService;

    @Autowired
    public SimulationController(ISimulationService simulationService){
        this.simulationService = simulationService;
    }

    @GetMapping(value = "/monty-hall", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<MontyHallSimulationResult>> getMontyHallSimulation(
            @RequestParam @NotNull @Min(1) @Max(Integer.MAX_VALUE) int numberOfSimulations,
            @RequestParam boolean switchCase) {

        CompletableFuture<MontyHallSimulationResult> simulationResult = simulationService.runMontyHallSimulation(numberOfSimulations, switchCase);

        return simulationResult
                .thenApplyAsync(result -> {
                    logger.info("Served {} simulations with switch case set to {}",numberOfSimulations, switchCase);
                    return ResponseEntity.ok(result);
                })
                .exceptionally(exception -> {
                    logger.error(exception.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @ExceptionHandler({ValidationException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String exceptionHandlerParamExceptions(Exception exception){
        logger.warn("Request was denied with cause: {}", exception.getMessage());
        // TODO: should not leak internal error info
        return exception.getMessage();
    }

}
