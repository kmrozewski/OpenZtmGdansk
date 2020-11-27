import React from 'react'
import {connect} from "react-redux";
import {Accordion, Badge, Card} from "react-bootstrap";
import VehicleMap from "../map/VehicleMap";


class BusMap extends React.Component {

    getLatLng = (vehicleId) => {
        if (vehicleId in this.props.vehicleLocation.vehicles) {
            const vehicle = this.props.vehicleLocation.vehicles[vehicleId]

            return {
                found: true,
                name: vehicle.VehicleCode,
                lat: vehicle.Lat,
                lon: vehicle.Lon
            }
        }

        return {
            found: false
        }
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        return this.props.vehicleLocation.lastUpdate !== nextProps.vehicleLocation.lastUpdate
    }

    render() {
        const coords = this.props.vehicleIds.map(this.getLatLng).filter(vehicle => vehicle.found)
        console.log('[BusMap] coords', coords)

        return (
            <>
                <Accordion defaultActiveKey="0">
                    <Card>
                        <Accordion.Toggle as={Card.Header} variant="link" eventKey="0">
                            <Badge pill variant="info">!</Badge> Pojazdy na mapie
                            <span className="sr-only">unread messages</span>
                        </Accordion.Toggle>
                        <Accordion.Collapse eventKey="0">
                            <Card.Body>
                                {coords.length > 0 ? <VehicleMap mapRef={this.map}
                                                                 groupRef={this.group}
                                                                 isLocateEnabled={false}
                                                                 coords={coords}
                                /> : <span></span>}
                            </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
            </>
        )
    }
}

function mapStateToProps(state) {
    return {
        vehicleLocation: state.vehicleLocation
    }
}

export default connect(mapStateToProps)(BusMap)