import React from 'react'
import {Tabs, Tab} from 'react-bootstrap'
import Delay from './Delay'

const stopTabs = [
    {key: "1", title: "Wołkowyska-Centrum", stopIds: [1848, 14547]},
    {key: "2", title: "Wołkowyska-Jasień", stopIds: [1849]},
    {key: "3", title: "Warneńska-Centrum", stopIds: [1374, 14540]},
    {key: "4", title: "Warneńska-Migowo", stopIds: [1371, 14539]},
]

export default class Estimate extends React.Component {
    render() {
        return (
            <Tabs>
                {stopTabs.map(this.renderTab)}
            </Tabs>
        )
    }

	renderTab = (stop) => {
		return (
			<Tab key={stop.key} eventKey={stop.key} title={stop.title}>
				<Delay stopKey={stop.title} stopIds={stop.stopIds}/>
			</Tab>
		)
	}
}