package se.comhem.test.montyhall.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import se.comhem.test.montyhall.controllers.SimulationController;
import se.comhem.test.montyhall.model.MontyHallSimulationJob;
import se.comhem.test.montyhall.model.MontyHallSimulationResult;
import se.comhem.test.montyhall.services.helpers.SimulationHelper;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class SimulationService implements ISimulationService{

    private static final Logger logger = LoggerFactory.getLogger(SimulationService.class);

    private final SimulationHelper simulationHelper;
    private final Executor taskExecutor;
    public SimulationService(@Qualifier("taskExecutor") Executor taskExecutor){
        this.simulationHelper = new SimulationHelper();
        this.taskExecutor = taskExecutor;
    }

    @Override
    public CompletableFuture<MontyHallSimulationResult> runMontyHallSimulation(int numberOfSimulations, boolean switchCase) {

        int simulationsPerThread = 10;
        List<Integer> workPerThread = simulationHelper.getWorkAmount(numberOfSimulations, simulationsPerThread);
        ArrayList<CompletableFuture<MontyHallSimulationResult>> futures = new ArrayList<>();

        workPerThread.forEach(work -> futures.add(runSimulationPart(work, switchCase)));

        long start = System.nanoTime();
        try {
            List<MontyHallSimulationResult> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(MontyHallSimulationResult.mergeResults(results));
        } catch (CompletionException | CancellationException ex){
            return CompletableFuture.failedFuture(ex);
        } finally {
            logger.info("Simulations took : {} ms",String.format("%.2f",(System.nanoTime() - start) / 1000000f));
        }
    }

    private CompletableFuture<MontyHallSimulationResult> runSimulationPart(int numberOfSimulations, boolean switchCase){
        return CompletableFuture.supplyAsync(new MontyHallSimulationJob(numberOfSimulations, switchCase), taskExecutor);
    }
}
