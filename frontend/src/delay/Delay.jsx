import React from 'react'
import styles from './Delay.css'
import Bus from "../bus/Bus"

export default class Delay extends React.PureComponent {

    renderBus = (delay, index) => <Bus key={index} className={styles.top10} delay={delay}/>

    render() {
        console.log('[Delay] render', this.props.stopCode, this.props)

        if (this.props.delays && this.props.delays.length > 0) {
            return this.props.delays.map(this.renderBus)
        }

        return (
            <h3>Brak pojazdów</h3>
        )
    }
}