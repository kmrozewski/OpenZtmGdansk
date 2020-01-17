import React from "react"
import {Map, Marker, Popup, TileLayer} from "react-leaflet"
import LocateControl from './LocateControl'
import {getNearestStops} from "../global/api";
import {Link} from "react-router-dom";


const position = [54.4270251, 18.5232501]

const locateOptions = {
    position: "topright",
    watch: true,
    cacheLocation: true,
    onActivate: () => {} // callback before engine starts retrieving locations
}

export default class Nearest extends React.Component {

    constructor() {
        super()
        this.state = {
            position: {
                lat: null,
                lnt: null
            },
            stops: []
        }
    }

    handleLocationUpdate = (event) => {
        this.setState({
            ...this.state,
            position: {
                lat: event.latitude,
                lng: event.longitude
            }
        })
    }

    shouldUpdateNearestStops = (prevState, currState) => {
        return prevState.position.lat !== currState.position.lat
        && prevState.position.lng !== currState.position.lng
        && prevState.stops.length !== currState.position.length
    }

    findNearestStops = async () => {
        if (this.state.position.lat && this.state.position.lng) {
            //TODO range and limit should be parametrized from the app
            const stops = await getNearestStops(this.state.position.lat, this.state.position.lng, 500, 30)

            this.setState({
                ...this.state,
                stops: stops
            })
        }
    }

    async componentDidUpdate(prevProps, prevState) {
        if (this.shouldUpdateNearestStops(prevState, this.state)) {
            await this.findNearestStops()
        }
    }

    render() {
        return (
            <Map center={position} zoom={11} maxZoom={18} onLocationFound={this.handleLocationUpdate}>
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                />
                <LocateControl options={locateOptions} startDirectly/>
                {this.state.stops.map((stop, index) => this.addMarker(stop, index))}
            </Map>
        )
    }

    addMarker = (stop, index) => {
        return (
            <Marker key={"marker-" + index} position={[stop.coords.lat, stop.coords.lon]}>
                <Popup>
                    <span>
                        <Link to={"/stop/" + stop.name + "/" + stop.code + "/"}>
                            {stop.name} {stop.code}
                        </Link>
                    </span>
                </Popup>
            </Marker>
        )
    }
}