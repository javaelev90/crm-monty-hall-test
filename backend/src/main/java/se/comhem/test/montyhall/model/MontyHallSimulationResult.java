package se.comhem.test.montyhall.model;

import java.util.List;

public class MontyHallSimulationResult {

    private int correctChoices;
    private int incorrectChoices;

    public MontyHallSimulationResult(){
    }
    public MontyHallSimulationResult(int correctChoices, int incorrectChoices){
        this.correctChoices = correctChoices;
        this.incorrectChoices = incorrectChoices;
    }

    public int getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(int correctChoices) {
        this.correctChoices = correctChoices;
    }

    public int getIncorrectChoices() {
        return incorrectChoices;
    }

    public void setIncorrectChoices(int incorrectChoices) {
        this.incorrectChoices = incorrectChoices;
    }

    public static MontyHallSimulationResult mergeResults(List<MontyHallSimulationResult> simulationResultList){
        MontyHallSimulationResult result = new MontyHallSimulationResult();
        for(MontyHallSimulationResult simulationResult : simulationResultList) {
            result.setCorrectChoices(result.getCorrectChoices() + simulationResult.getCorrectChoices());
            result.setIncorrectChoices(result.getIncorrectChoices() + simulationResult.getIncorrectChoices());
        }
        return result;
    }

}
