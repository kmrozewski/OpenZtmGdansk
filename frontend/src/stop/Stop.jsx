import React from 'react'
import {Redirect} from 'react-router'
import {getStopByName} from '../global/api'
import {stops as stopNames} from './stops'
import * as StopActions from "./actions"
import {connect} from "react-redux"
import Delay from "../delay/Delay"
import {Tab, Tabs} from "react-bootstrap"

class Stop extends React.Component {

    async componentDidMount() {
        let stop = await getStopByName(this.props.stopName)
        this.props.onStopSearched(stop)
    }

    isStopNameValid = () => stopNames.includes(this.props.stopName)

    renderStopCodes() {
        return (
        	<Tabs>
				{this.props.stop.codes.map(this.renderStopCode)}
			</Tabs>
		)
    }

    renderStopCode = (stopCode) => {
		let title = this.props.stopName + ' ' + stopCode.code
		let stopIds = stopCode.stops.map(s => s.id)

		return (
			<Tab key={title} eventKey={title} title={title}>
				<Delay stopIds={stopIds}/>
			</Tab>
		)
	}

    render() {
        if (!this.isStopNameValid()) {
            return <Redirect push to="/404"/>
        }

        if (this.props.stop.codes) {
            return this.renderStopCodes()
        }

        return (
            <h3>Przystanek {this.props.stopName}</h3>
        )
    }
}

function mapStateToProps(state, ownProps) {
    return {
        ownProps,
        stop: state.stop
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onStopSearched: (stop) => dispatch(StopActions.stopsRefreshed(stop))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Stop)