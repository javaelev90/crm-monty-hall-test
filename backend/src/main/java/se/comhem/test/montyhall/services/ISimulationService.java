package se.comhem.test.montyhall.services;

import se.comhem.test.montyhall.model.MontyHallSimulationResult;
import java.util.concurrent.CompletableFuture;

public interface ISimulationService {
    CompletableFuture<MontyHallSimulationResult> runMontyHallSimulation(int numberOfSimulations, boolean switchCase);
}
