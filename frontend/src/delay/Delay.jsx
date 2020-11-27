import React from 'react'
import styles from './Delay.css'
import Bus from "../bus/Bus"
import {getDelaysAggregated} from "../global/api";
import Alert from "react-bootstrap/Alert";
import BusMap from "../bus/BusMap";

const INTERVAL_SECONDS = 10000

//TODO introduce Spinner for every usage of Delay component
export default class Delay extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            delays: [],
            time: Date.now()
        }
    }

    async componentDidMount() {
        this.updateDelays()

        this.interval = setInterval(async () => {
            await this.updateDelays()

            this.setState({...this.state, time: Date.now()})
        }, INTERVAL_SECONDS)
    }

    async componentWillUnmount() {
        clearInterval(this.interval)
    }

    async componentDidUpdate(prevProps, prevState) {
        if (JSON.stringify(prevProps.stopIds) !== JSON.stringify(this.props.stopIds)) {
            this.updateDelays()
        }
    }

    updateDelays = async () => {
        const delays = await this.getDelays(this.props.stopIds)

        this.setState({
            ...this.state,
            delays: delays
        })
    }

    getVehicleIds = (delays) => delays.filter(delay => delay.vehicleId).map(delay => delay.vehicleId)

    renderBus = (delay, index) => <Bus key={index} className={styles.top10} delay={delay}/>

    render() {
        if (this.state.delays.length > 0) {
            return (
                <>
                    <BusMap vehicleIds={this.getVehicleIds(this.state.delays)}/>
                    {this.state.delays.map(this.renderBus)}
                </>
            )
        }

        return (<h3>Brak pojazdów</h3>)
    }

    getDelays = async (stopIds) => {
        const result = await getDelaysAggregated(stopIds)

        return result.delays
    }
}