function updateSimulationData(correct, incorrect) {
    return {
        labels: [
            'Correct',
            'Incorrect',
        ],
        datasets: [{
            label: 'Monty Hall simulation result',
            data: [correct, incorrect],
            backgroundColor: [
                'rgb(99,255,102)',
                'rgb(238,93,73)',
            ],
            hoverOffset: 4
        }]
    }
}

export default updateSimulationData;