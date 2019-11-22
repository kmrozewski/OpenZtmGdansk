import React from 'react'
import {Tabs, Tab} from 'react-bootstrap'
import Delay from './Delay'
import {getDelaysAggregated} from "../global/api"

const stopTabs = [
    {key: "1", title: "Wołkowyska-Centrum", stopIds: [1848, 14547]},
    {key: "2", title: "Wołkowyska-Jasień", stopIds: [1849]},
    {key: "3", title: "Warneńska-Centrum", stopIds: [1374, 14540]},
    {key: "4", title: "Warneńska-Migowo", stopIds: [1371, 14539]},
]

export default class Estimate extends React.Component {

    constructor() {
        super()

        this.state = {
            delayMap: {}
        }
    }

    async componentDidMount() {
        const delayMap = await stopTabs.map(this.mapStop)

		this.setState({delayMap})

		console.log('[Estimate] did mount', '\nprops', this.props, '\nstate', this.state)
    }

    async componentDidUpdate() {
		console.log('[Estimate] did update', '\nprops', this.props, '\nstate', this.state)
	}

    render() {
        return (
            <Tabs>
                {stopTabs.map(this.renderTab.bind(this))}
            </Tabs>
        )
    }

	renderTab(stop) {
		console.log('[Estimate] render', '\nprops', this.props, '\nstate', this.state)

    	if (this.state.delayMap) {
			return (
				<Tab key={stop.key} eventKey={stop.key} title={stop.title}>
					<Delay stopIds={stop.stopIds} delays={this.state.delayMap[stop.key]}/>
				</Tab>
			)
		}

		return (
			<Tab key={stop.key} eventKey={stop.key} title={stop.title}>
				<Delay stopIds={stop.stopIds}/>
			</Tab>
		)
	}

	mapStop = async (stop) => {
    	return {
			key   : stop.key,
			delays: await this.getDelays(stop.stopIds)
		}
	}

	getDelays = async (stopIds) => {
		console.log('[Estimate] getDelays')
		const result = await getDelaysAggregated(stopIds)

		return result.delays
	}
}