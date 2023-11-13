import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import React from "react";


export function SimulationForm(props){
    return (
        <Form>
            <Form.Group className="mb-3">
                <Form.Label>Number of simulations:</Form.Label>
                <Form.Control min="1" max="2147483647" type="number" required="required" placeholder="Enter number of simulations" value={props.numberOfSimulations} onChange={props.handleSimulationChange} />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>Switch case:</Form.Label>
                <Form.Check type="checkbox" value={props.switchCase} onChange={event => props.setSwitchCase(event.target.checked)}/>
            </Form.Group>

            <Form.Group className="rowAlignment leftAlignment">
                <Button className="btn btn-primary" type="submit" onClick={props.getSimulation} disabled={props.isLoading}>Simulate</Button>
                {props.isLoading && <Spinner animation="border"/>}
            </Form.Group>
        </Form>
    )
}