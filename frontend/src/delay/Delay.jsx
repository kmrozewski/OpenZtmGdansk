import React from 'react'
import {getDelaysAggregated} from '../global/api'
import Bus from "../bus/Bus"
import styles from './Delay.css'

export default class Delay extends React.Component {

    constructor() {
        super();

        this.state = {
            isLoading: true,
            delays   : []
        }
    }

    async componentDidMount() {
        console.log('did mount', this.props)
        const result = await getDelaysAggregated(this.props.stopIds)
        this.setState(result);

        console.log(this.state);
    }

    render() {
        if (this.state.delays.length > 0) {
            return this.state.delays.map((delay, index) => <Bus
                key={index}
                className={styles.top10}
                delay={delay} />)
        }

        return (
            <h3>Brak pojazdów</h3>
        )
    }
}