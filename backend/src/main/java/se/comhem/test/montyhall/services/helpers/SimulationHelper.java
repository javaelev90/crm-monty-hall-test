package se.comhem.test.montyhall.services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SimulationHelper {

    public List<Integer> getWorkAmount(int numberOfSimulations, int numberOfThreads){
        ArrayList<Integer> workAmounts = new ArrayList<>();
        int workAmount = numberOfSimulations / numberOfThreads;
        int remainder = numberOfSimulations % numberOfThreads;

        for(int i = 0; i < numberOfThreads; i++)
            workAmounts.add(workAmount);

        if(remainder > 0)
            workAmounts.add(numberOfThreads - 1, remainder);

        return workAmounts;
    }
}
