import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import React from "react";


export function SimulationForm(){

    return (
        <Form>
            <Form.Group className="mb-3">
                <Form.Label>Number of simulations:</Form.Label>
                <Form.Control min="1" max="2147483647" type="number" required="required" placeholder="Enter number of simulations" value={numberOfSimulations} onChange={handleSimulationChange} />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>Switch case:</Form.Label>
                <Form.Check type="checkbox" value={switchCase} onChange={event => setSwitchCase(event.target.checked)}/>
            </Form.Group>

            <Form.Group className="rowAlignment leftAlignment">
                <Button className="btn btn-primary" type="submit" onClick={getSimulation} disabled={isLoading}>Simulate</Button>
                {isLoading && <Spinner animation="border"/>}
            </Form.Group>
        </Form>
    )
}