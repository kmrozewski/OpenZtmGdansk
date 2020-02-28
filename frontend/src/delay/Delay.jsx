import React from 'react'
import styles from './Delay.css'
import Bus from "../bus/Bus"
import {getDelaysAggregated} from "../global/api";

//TODO introduce Spinner for every usage of Delay component
export default class Delay extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            delays: []
        }
    }

    async componentDidMount() {
        console.log('[Delay] mounted')
        this.updateDelays()
    }

    async componentDidUpdate(prevProps, prevState) {
        if (JSON.stringify(prevProps.stopIds) !== JSON.stringify(this.props.stopIds)) {
            console.log('[Delay] updated ', prevProps.stopKey, prevProps.stopIds)
            this.updateDelays()
        }
    }

    updateDelays = async () => {
        const delays = await this.getDelays(this.props.stopIds)

        this.setState({
            delays: delays
        })
    }

    renderBus = (delay, index) => <Bus key={index} className={styles.top10} delay={delay}/>

    render() {
        if (this.state.delays.length > 0) {
            return this.state.delays.map(this.renderBus)
        }

        return (
            <h3>Brak pojazdów</h3>
        )
    }

    getDelays = async (stopIds) => {
        const result = await getDelaysAggregated(stopIds)

        return result.delays
    }
}