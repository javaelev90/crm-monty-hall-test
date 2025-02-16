import React from "react";
import './Simulation.css';
import {useState, useEffect, useCallback} from "react";
import axios from "axios";
import Spinner from 'react-bootstrap/Spinner';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { Doughnut } from "react-chartjs-2";
import updateSimulationData from "./util/chartUtils"
import {Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import {SimulationForm} from "./form/Form";

ChartJS.register(ArcElement, Tooltip, Legend);

function Simulation() {
    const [simulationResult, setSimulationResult] = useState(null);
    const [chartData, setChartData] = useState(updateSimulationData(0,0));
    const [isLoading, setIsLoading] = useState(false);
    const [numberOfSimulations, setNumberOfSimulations] = useState(1);
    const [switchCase, setSwitchCase] = useState(false);

    const getSimulation = useCallback(async (event) => {
        event.preventDefault();
        setIsLoading(true);
        axios.get("simulation/monty-hall", { params : { numberOfSimulations : numberOfSimulations, switchCase : switchCase } })
            .then(res => {
                if (res.status === 200) {
                    setSimulationResult(res.data);
                }
            })
            .catch(res => {
                console.log(res);
            })
            .finally(() => setIsLoading(false));

    }, [numberOfSimulations, switchCase, isLoading]);

    const handleSimulationChange = useCallback((event) => {
        let {value, min, max} = event.target;
        setNumberOfSimulations(Math.max(Number(min), Math.min(Number(max), Number(value))));
    }, [numberOfSimulations]);

    useEffect(() => {
        if(simulationResult !== null)
            setChartData(updateSimulationData(simulationResult["correctChoices"], simulationResult["incorrectChoices"]))
    }, [simulationResult]);

    return (
        <div className="simulation columnAlignment centerAlignment simulation-container">
            <SimulationForm switchCase={switchCase}
                            setSwitchCase={setSwitchCase}
                            getSimulation={getSimulation}
                            isLoading={isLoading}
                            numberOfSimulations={numberOfSimulations}
                            handleSimulationChange={handleSimulationChange}
            />
            <div className="centerAlignment">
                {simulationResult != null && <Doughnut data={chartData}/>}
            </div>
        </div>
    );
}

export default Simulation;
