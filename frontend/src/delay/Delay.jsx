import React from 'react'
import styles from './Delay.css'
import Bus from "../bus/Bus"

//TODO introduce Spinner for every usage of Delay component
export default class Delay extends React.Component {

    renderBus = (delay, index) => <Bus key={index} className={styles.top10} delay={delay}/>

    render() {
        if (this.props.delays && this.props.delays.length > 0) {
            return this.props.delays.map(this.renderBus)
        }

        return (
            <h3>Brak pojazdów</h3>
        )
    }
}