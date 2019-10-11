import React from 'react'
import axios from 'axios';
import {API} from '../global/const'
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
        try {
            const result = await axios.get(API + "estimate/1397")

            this.setState({
                isLoading: false,
                delays   : result.data.delay
            })
        } catch (error) {
            this.setState({
                isLoading: false,
                delays   : []
            })
        }

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