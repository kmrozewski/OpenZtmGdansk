import React from "react";
import {FeatureGroup, Map, Marker, Popup, TileLayer} from "react-leaflet";
import {Link} from "react-router-dom";
import LocateControl from "../nearest/LocateControl";

const position = [54.4270251, 18.5232501]
const locateOptions = {
    position: "topright",
    watch: true,
    cacheLocation: true,
    onActivate: () => {
    } // callback before engine starts retrieving locations
}

export default class VehicleMap extends React.Component {

    constructor(props) {
        super(props)

        this.mapRef = React.createRef()
    }

    onFeatureGroupAdd = (event) => {
        let bounds = event.target.getBounds()
        this.mapRef.current.leafletElement.fitBounds(bounds)
    }

    addMarker = (position, index) => {
        return (
            <Marker key={"marker-" + index} position={[position.lat, position.lon]}>
                <Popup>
                    <span>
                        {position.name}
                    </span>
                </Popup>
            </Marker>
        )
    }

    render() {
        return (
            <React.Fragment>
                <Map ref={this.mapRef} center={position} zoom={11} maxZoom={18} onLocationFound={this.props.isLocateEnabled ? this.props.handleLocationUpdate : () => {}}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
                    />
                    {this.props.isLocateEnabled ? <LocateControl options={locateOptions} startDirectly/> : <span/>}
                    <FeatureGroup onAdd={this.onFeatureGroupAdd}>
                        {this.props.coords.map((position, index) => this.addMarker(position, index))}
                    </FeatureGroup>
                </Map>
            </React.Fragment>
        )
    }
}