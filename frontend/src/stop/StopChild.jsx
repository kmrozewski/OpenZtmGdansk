import React from 'react'
import StopCodeList from "./StopCodeList"
import Delay from "../delay/Delay"
import {connect} from "react-redux"
import {getDelaysAggregated} from "../global/api"


class StopChild extends React.Component {

    constructor() {
        super()

        this.state = {
            isLoading: true,
            stopIds  : [],
            delays   : []
        }
    }

    async componentDidMount() {
        if (this.props.stop.codes && this.props.stop.codes.length > 0) {
            await this.getDelays()
        }
    }


    async componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.stopCode !== prevProps.stopCode || this.state.stopIds.length === 0) {
            await this.getDelays()
        }
    }

    renderDelay() {
        if (this.state.stopIds.length > 0) {
            return (<Delay stopIds={this.state.stopIds} stopCode={this.props.stopCode} delays={this.state.delays}/>)
        }
    }

    render() {
        return (
            <React.Fragment>
                <StopCodeList stopName={this.props.stopName} title={this.props.stopName + ' ' + this.props.stopCode}/>
                {this.renderDelay()}
            </React.Fragment>
        )
    }

    findStopCode = () => this.props.stop.codes.filter(c => c.code === this.props.stopCode)
    getStopIds = () => this.findStopCode()[0].stops.map(s => s.id)

    async getDelays() {
        const stopIds = this.getStopIds()
        const result = await getDelaysAggregated(stopIds)

        this.setState({
            ...this.state,
            ...result,
            stopIds: stopIds,
        })
    }
}

function mapStateToProps(state, ownProps) {
    return {
        ownProps,
        stop: state.stop
    }
}

export default connect(mapStateToProps)(StopChild)