import React from "react";
import './Simulation.css';
import {useState, useEffect} from "react";
import axios from "axios";
import Spinner from 'react-bootstrap/Spinner';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { Doughnut } from "react-chartjs-2";
import updateSimulationData from "./util/chartUtils"
import {Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

function Simulation() {
    const [simulationResult, setSimulationResult] = useState(null);
    const [chartData, setChartData] = useState(updateSimulationData(0,0));
    const [isLoading, setIsLoading] = useState(false);
    const [numberOfSimulations, setNumberOfSimulations] = useState(1);
    const [switchCase, setSwitchCase] = useState(false);

    const getSimulation = async (event) => {
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

    }

    useEffect(() => {
        if(simulationResult !== null)
            setChartData(updateSimulationData(simulationResult["correctChoices"], simulationResult["incorrectChoices"]))
    }, [simulationResult]);

    return (
        <div className="simulation columnAlignment centerAlignment simulation-container">
            <Form>
                <Form.Group className="mb-3">
                    <Form.Label>Number of simulations:</Form.Label>
                    <Form.Control min="1" max="1000000000" type="number" required="required" placeholder="Enter number of simulations" value={numberOfSimulations} onChange={event => setNumberOfSimulations(event.target.value)} />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Switch case:</Form.Label>
                    <Form.Check type="checkbox" value={switchCase} onChange={event => setSwitchCase(event.target.checked)}/>
                </Form.Group>

                <Form.Group className="rowAlignment leftAlignment">
                    <Button className="btn btn-primary" type="submit" onClick={getSimulation} disabled={isLoading}>Simulate</Button>
                    <Spinner animation="border" hidden={!isLoading} />
                </Form.Group>
            </Form>
            <div className="centerAlignment" >
                <Doughnut
                    data={chartData}
                />
            </div>
        </div>
    );
}

export default Simulation;
