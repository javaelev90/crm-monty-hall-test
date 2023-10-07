package se.comhem.test.montyhall.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import se.comhem.test.montyhall.model.MontyHallSimulationResult;
import se.comhem.test.montyhall.services.SimulationService;

import javax.validation.ValidationException;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulationController.class)
public class SimulationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SimulationService service;

    @Test
    public void getMontyHallSimulationTest() throws Exception {
        CompletableFuture<MontyHallSimulationResult> result = CompletableFuture.completedFuture(new MontyHallSimulationResult(700,300));
        given(service.runMontyHallSimulation(1000, true)).willReturn(result);

        MvcResult mvcResult = mvcPerform(1000, true)
                .andExpect(request().asyncStarted()).andReturn();

        mvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("correctChoices").value(700))
                .andExpect(jsonPath("incorrectChoices").value(300));

    }

    @Test
    public void getMontyHallSimulationExceptionTest() throws Exception {
        CompletableFuture<MontyHallSimulationResult> simulationResult = CompletableFuture.failedFuture(new Exception());
        given(service.runMontyHallSimulation(-1, false)).willReturn(simulationResult);

        mvcPerform(-1, true)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }


    private ResultActions mvcPerform(int numberOfSimulations, boolean switchCase) throws Exception {
        return mvc.perform(get("/simulation/monty-hall")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numberOfSimulations", String.valueOf(numberOfSimulations))
                .param("switchCase", String.valueOf(switchCase))
        );
    }
}
