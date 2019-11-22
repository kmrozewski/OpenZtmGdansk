import React from 'react'
import {connect} from "react-redux"
import {getStopByName} from "../global/api"
import * as StopActions from "./actions"
import {Link} from "react-router-dom"

class StopCodeList extends React.Component {

    async componentDidMount() {
        let stop = await getStopByName(this.props.stopName)
        this.props.onStopSearched(stop)
    }

    renderStopCodes = (code, index) => {
        return (
            <li key={index}>
                <Link to={this.getPath(code.code)}>
                    {this.props.stopName} {code.code}
                </Link>
            </li>
        )
    }

    render() {
        if (this.props.stop.codes) {
            return (
                <React.Fragment>
                    <h3>{this.props.title}</h3>
                    Lista przystanków:
                    <ul>
                        {this.props.stop.codes.map(this.renderStopCodes)}
                    </ul>
                </React.Fragment>
            )
        }

        return (<h3>{this.props.title}</h3>)
    }

    getPath = (code) => '/stop/' + this.props.stopName + '/' + code + '/'
}

function mapStateToProps(state, ownProps) {
    return {
        ownProps,
        stop: state.stop
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onStopSearched: (stop) => dispatch(StopActions.stopsRefreshed(stop))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(StopCodeList)