import React from 'react'
import {Redirect} from 'react-router'
import {getStopByName} from '../global/api'
import * as StopActions from "./actions"
import {connect} from "react-redux"
import Delay from "../delay/Delay"
import {Tab, Tabs} from "react-bootstrap"
import {isStopNameValid} from "../search/Search"

class Stop extends React.Component {

    async componentDidMount() {
        console.log('[Stop] mounted')
        let stop = await getStopByName(this.props.stopName)
        console.log('[Stop] ', stop)
        this.props.onStopSearched(stop)
    }

    renderStopCodes = () => {
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
        if (!isStopNameValid(this.props.stopName, this.props.stopNames)) {
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
        stop: state.stop,
        stopNames: state.stopNames
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onStopSearched: (stop) => dispatch(StopActions.stopsRefreshed(stop))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Stop)