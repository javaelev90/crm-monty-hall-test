package se.comhem.test.montyhall.model;

import java.util.Random;
import java.util.function.Supplier;

public class MontyHallSimulationJob implements Supplier<MontyHallSimulationResult> {

    private final int numberOfSimulations;
    private final boolean switchCase;

    public MontyHallSimulationJob(int numberOfSimulations, boolean switchCase){
        this.numberOfSimulations = numberOfSimulations;
        this.switchCase = switchCase;
    }
    @Override
    public MontyHallSimulationResult get() {
        Random random = new Random();

        int numberOfCorrectChoices = 0;
        int numberOfIncorrectChoices = 0;

        for (int i = 0; i < numberOfSimulations; i++){
            int correctChoice = random.nextInt(3);
            int playerChoice = random.nextInt(3);

            boolean playerChoseCorrectly = correctChoice == playerChoice;

            // Chooses correctly but switches
            if(playerChoseCorrectly && switchCase){
                numberOfIncorrectChoices++;
            // Chooses correctly and doesn't switch
            } else if(playerChoseCorrectly && !switchCase) {
                numberOfCorrectChoices++;
            // Chooses incorrect but switches
            } else if(!playerChoseCorrectly && switchCase) {
                numberOfCorrectChoices++;
            // Chooses incorrect and doesn't switch
            } else if(!playerChoseCorrectly && !switchCase) {
                numberOfIncorrectChoices++;
            }
        }
        return new MontyHallSimulationResult(numberOfCorrectChoices,numberOfIncorrectChoices);
    }
}
