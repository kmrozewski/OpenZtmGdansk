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
        const delayMap = await Promise.all(stopTabs.map(this.mapStop))
		this.setState({delayMap})
    }

    render() {
        return (
            <Tabs>
                {stopTabs.map(this.renderTab.bind(this))}
            </Tabs>
        )
    }

	renderTab(stop) {
    	if (this.state.delayMap[stop.key]) {
			return (
				<Tab key={stop.key} eventKey={stop.key} title={stop.title}>
					<Delay stopIds={stop.stopIds} delays={this.state.delayMap[stop.key].delays}/>
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
		const result = await getDelaysAggregated(stopIds)

		return result.delays
	}
}