import React from 'react'
import {getDelaysAggregated} from '../global/api'
import styles from './Delay.css'
import Bus from "../bus/Bus"
import {intersection} from "../global/util"

export default class Delay extends React.Component {

    constructor() {
        super();

        this.state = {
            isLoading: true,
            delays   : []
        }
    }

    shouldComponentUpdate(nextProps) {
        return intersection(nextProps.stopIds, this.props.stopIds).length > 0
    }

    async componentDidMount() {
        console.log('[Delay] mount', this.props.stopCode, this.props.stopIds)
        const result = await getDelaysAggregated(this.props.stopIds)
        this.setState(result);
    }

    async componentDidUpdate(prevProps) {
        if (prevProps.stopCode !== this.props.stopCode) {
            const result = await getDelaysAggregated(this.props.stopIds)
            this.setState(result);
        }
    }

    renderBus = (delay, index) => <Bus key={index} className={styles.top10} delay={delay}/>

    render() {
        console.log('[Delay] render', this.props.stopCode, this.state, this.props)

        if (this.state.delays.length > 0 && this.props.stopIds && this.props.stopIds.length > 0) {
            return this.state.delays.map(this.renderBus)
        }

        return (
            <h3>Brak pojazdów</h3>
        )
    }
}