import React from 'react'
import {getStopByName} from "../global/api"
import {Link} from "react-router-dom"
import {Accordion, Card} from "react-bootstrap"
import {isStopNameValid} from '../search/Search'
import StopMap from "./StopMap";
import {connect} from "react-redux";

class StopCodeList extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            stop: {}
        }
    }

    async componentDidMount() {
        const stop = await getStopByName(this.props.stopName)
        console.log('[StopCodeList] ', stop)
        this.setState({stop: stop})

        console.log('[StopCodeList] stop', stop)
    }

    getStops = () => {
        const stopName = this.props.stopName
        const stops = Object.values(this.state.stop)

        return stops.map(s => ({id: s.stopId, name: s.stopName, code: s.stopCode, coords: [s.stopLon, s.stopLat]}))

        // return this.state.stop.codes.flatMap(code => {
        //     const stopCode = code.code
        //     return code.stops.map(s => ({
        //         id: s.id,
        //         name: stopName,
        //         code: stopCode,
        //         coords: s.coords
        //     }))
        // })
    }

    renderLeafletMapWithStops = () => {
        if (this.state.stop.codes && this.state.stop.codes.length > 0) {
            const stops = this.getStops()
            return (<StopMap mapRef={this.map} groupRef={this.group} isLocateEnabled={false} stops={stops}/>)
        }
    }

    renderStopCodes = (stop, index) => {
        return (
            <li key={index}>
                <Link to={this.getPath(stop.stopCode)}>
                    {this.props.stopName} {stop.stopCode}
                </Link>
            </li>
        )
    }

    render() {
        if (this.state.stop && isStopNameValid(this.props.stopName, this.props.stopNames)) {
            return (
                <Accordion defaultActiveKey="0">
                    <Card>
                        <Accordion.Toggle as={Card.Header} variant="link" eventKey="0">
                            Lista przystanków - {this.props.stopName}
                        </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                            <Card.Body>
                                <ul>
                                    {Object.values(this.state.stop).map(this.renderStopCodes)}
                                </ul>
                                {this.renderLeafletMapWithStops()}
                            </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
            )
        }

        return (<h3>Nie ma przystanku o nazwie "{this.props.title}"</h3>)
    }

    getPath = (code) => '/stop/' + this.props.stopName + '/' + code + '/'
}

function mapStateToProps(state) {
    return {
        stopNames: state.stopNames
    }
}

export default connect(mapStateToProps)(StopCodeList)