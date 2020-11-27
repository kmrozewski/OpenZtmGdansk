import React from "react"
import {Map, Marker, Popup, TileLayer} from "react-leaflet"
import LocateControl from './LocateControl'
import {getNearestStops} from "../global/api";
import {Link} from "react-router-dom";
import NearestParams from "./NearestParams";
import {connect} from "react-redux";
import * as SpinnerActions from "../spinner/actions";
import Spinner from "../spinner/withSpinner";


const position = [54.4270251, 18.5232501]
const locateOptions = {
    position: "topright",
    watch: true,
    cacheLocation: true,
    onActivate: () => {
    } // callback before engine starts retrieving locations
}

class Nearest extends React.Component {

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

    areNearestParamsChanged = (prevProps, currProps) => {
        return prevProps.range !== currProps.range
            || prevProps.limit !== currProps.limit
    }

    findNearestStops = async () => {
        console.log('[Nearest] update props', this.props.range, this.props.limit)
        if (this.state.position.lat && this.state.position.lng) {
            this.props.onRefreshStarted()

            const stops = await getNearestStops(this.state.position.lat, this.state.position.lng, this.props.range, this.props.limit)

            console.log('[Nearest] stops: ', stops)

            this.setState({
                ...this.state,
                stops: stops
            })

            this.props.onRefreshStopped()
        }
    }

    async componentDidUpdate(prevProps, prevState) {
        if (this.shouldUpdateNearestStops(prevState, this.state) || this.areNearestParamsChanged(prevProps, this.props)) {
            await this.findNearestStops()
        }
    }

    renderMap = () => {
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

    render() {
        return (
            <>
                <NearestParams/>
                {this.props.isLoading ? <Spinner/> : this.renderMap()}
            </>
        )
    }

    //TODO (NŻ) doesn't work
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

function mapStateToProps(state) {
    return {
        range: state.nearestParams.range,
        limit: state.nearestParams.limit,
        isLoading: state.loading.isLoadingMap
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onRefreshStarted: () => dispatch(SpinnerActions.refreshStarted()),
        onRefreshStopped: () => dispatch(SpinnerActions.refreshStopped())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Nearest)