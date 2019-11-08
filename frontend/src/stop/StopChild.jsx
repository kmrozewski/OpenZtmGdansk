import React from 'react'
import StopCodeList from "./StopCodeList"
import Delay from "../delay/Delay"
import {connect} from "react-redux"


class StopChild extends React.Component {

    findStopCode = () => this.props.stop.codes.filter(c => c.code === this.props.stopCode)
    hasStopCode = () => this.findStopCode().length > 0

    renderDelay() {
        if (this.props.stop.codes && this.hasStopCode(this.props.stopCode)) {
            let stopIds = this.findStopCode()[0].stops.map(s => s.id)
            return (<Delay stopIds={stopIds} stopCode={this.props.stopCode}/>)
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log('[StopChild] update', this.props)
    }

    render() {
        console.log('[StopChild] render', this.props.stopName, this.props.stopCode)

        return (
            <React.Fragment>
                <StopCodeList stopName={this.props.stopName} title={this.props.stopName + ' ' + this.props.stopCode}/>
                {this.renderDelay()}
            </React.Fragment>
        )
    }
}

function mapStateToProps(state, ownProps) {
    return {
        ownProps,
        stop: state.stop
    }
}

export default connect(mapStateToProps)(StopChild)