import React from 'react'
import {Redirect} from 'react-router'
import {getStopByName} from '../global/api'
import {stops as stopNames} from '../global/stops'

export default class Stop extends React.Component {

	constructor(props) {
		super()

		this.state = {
			stops: {
				name: props.stopName,
				codes: []
			}
		}
	}

	async componentDidMount() {
		let result = await getStopByName(this.props.stopName)
		this.setState({stops: result})
	}

	isStopNameValid = () => stopNames.includes(this.props.stopName)

	render() {
		if (!this.isStopNameValid()) {
			return <Redirect push to="/404"/>
		}

		return (
			<h3>Henlo {this.props.stopName}</h3>
		)
	}
}