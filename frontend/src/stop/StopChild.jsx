import React from 'react'
import StopCodeList from "./StopCodeList"
import Delay from "../delay/Delay"
import {getStopIdsByNameAndCode} from "../global/api";
import Spinner from "../spinner/withSpinner";

export default class StopChild extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            stopIds: [],
        }
    }

    componentDidMount() {
        this.updateStopIds()
    }

    componentDidUpdate(prevProps) {
        if (prevProps.stopCode !== this.props.stopCode) {
            this.updateStopIds()
        }
    }

    render() {
        if (this.state.stopIds.length > 0) {
            return (
                <>
                    <StopCodeList stopName={this.props.stopName}
                                  title={this.props.stopName + ' ' + this.props.stopCode}/>

                    <Delay stopKey={this.props.stopCode} stopIds={this.state.stopIds}/>
                </>
            )
        }

        return (<Spinner/>)
    }

    updateStopIds = async () => {
        if (this.props.stopName && this.props.stopName) {
            const stopIds = await getStopIdsByNameAndCode(this.props.stopName, this.props.stopCode)
            this.setState({stopIds: stopIds})
        }
    }
}