import React from 'react'
import {Card} from "react-bootstrap"

export default class Bus extends React.Component {
    formatSeconds(delayInSeconds) {
        //https://stackoverflow.com/a/6313008
        let sec_num = parseInt(Math.abs(delayInSeconds), 10); // don't forget the second param
        let hours = Math.floor(sec_num / 3600);
        let minutes = Math.floor((sec_num - (hours * 3600)) / 60);
        let seconds = sec_num - (hours * 3600) - (minutes * 60);

        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }

        if (hours === '00') {
            return minutes + ':' + seconds;
        }

        return hours + ':' + minutes + ':' + seconds;
    }

    render() {
        return (
            <Card bg={this.props.delay.delayInSeconds < 0 ? "danger" : "success"} text="white" style={{marginTop: '1rem'}}>
                <Card.Header>{this.props.delay.headsign}</Card.Header>
                <Card.Body>
                    <Card.Text>{this.props.delay.delayInSeconds < 0 ? "Opóźniony o" : "Przyspieszony o"} {this.formatSeconds(this.props.delay.delayInSeconds)}</Card.Text>
                    <Card.Text>Czas estymowany: {this.props.delay.estimatedTime}</Card.Text>
                    <Card.Text>Czas według rozkładu: {this.props.delay.theoreticalTime}</Card.Text>
                </Card.Body>
            </Card>
        );
    }
}