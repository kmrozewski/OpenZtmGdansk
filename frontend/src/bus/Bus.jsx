import React from 'react'
import {Card} from "react-bootstrap"
import {getRouteById, getTripById} from '../global/api'
import {formatSeconds} from './util'

export default class Bus extends React.Component {

    constructor(props) {
        super()

        this.state = {
            routeNumber: props.delay.headsign,
            trip       : {}
        }
    }

    async componentDidMount() {
        const route = await getRouteById(this.props.delay.routeId)
        const trip = await getTripById(this.props.delay.routeId, this.props.delay.tripId)

        this.setState({
            routeNumber: route.routeShortName,
            trip       : trip
        })
    }

    renderHeader() {
        if (this.state.trip.tripHeadsign) {
            return this.state.routeNumber + " " + this.state.trip.tripHeadsign
        }
    }

    render() {
        return (
            <Card bg={this.props.delay.delayInSeconds < 0 ? "danger" : "success"} text="white" style={{marginTop: '1rem'}}>
                <Card.Header>
                    {this.renderHeader()}
                </Card.Header>
                <Card.Body>
                    <Card.Text>{this.props.delay.delayInSeconds < 0 ? "Opóźniony o" : "Przyspieszony o"} {formatSeconds(this.props.delay.delayInSeconds)}</Card.Text>
                    <Card.Text>Czas estymowany: {this.props.delay.estimatedTime}</Card.Text>
                    <Card.Text>Czas według rozkładu: {this.props.delay.theoreticalTime}</Card.Text>
                </Card.Body>
            </Card>
        );
    }
}