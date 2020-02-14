import React from "react"
import {connect} from "react-redux";
import {Accordion, Button, Card} from "react-bootstrap";
import Slider from "rc-slider";
import 'rc-slider/assets/index.css';
import * as NearestParamsActions from "./actions"
import * as SpinnerActions from "../spinner/actions"

const rangeMarks = {
    0: "0m",
    100: "100m",
    200: "200m",
    300: "300m",
    400: "400m",
    500: "500m",
    600: "600m",
    700: "700m",
    800: "800m",
    900: "900m",
    1000: "1000m",
    1100: "1100m",
    1200: "1200m"
}

const limitMarks = {
    5: '5',
    10: '10',
    15: '15',
    20: '20',
    25: '25',
    30: '30'
}

class NearestParams extends React.Component {

    handleStopRangeChange = (range) => {
        console.log('[NearestParams] updated range', range)
        // this.props.onRefreshStarted()

        this.props.onNearestParamsChanged({
            range: range,
            limit: this.props.limit
        })
    }

    handleStopLimitChange = (limit) => {
        // this.props.onRefreshStarted()
        console.log('[NearestParams] updated limit', limit)

        this.props.onNearestParamsChanged({
            range: this.props.range,
            limit: limit
        })
    }

    renderRangeSlider = () => {
        return (
            <>
                <p>Szukaj przystanków w zasięgu {this.props.range}m</p>
                <Slider min={0} max={1200} defaultValue={this.props.range} step={100} dots={true}
                        marks={rangeMarks}
                        onAfterChange={this.handleStopRangeChange} />
            </>
        )
    }

    renderLimitSlider = () => {
        return (
            <>
                <p>Ogranicz wyszukiwanie do {this.props.limit} przystanków</p>
                <Slider min={5} max={30} defaultValue={this.props.limit} step={5} dots={true}
                        marks={limitMarks}
                        style={{marginBottom: "2rem"}}
                        onAfterChange={this.handleStopLimitChange} />
            </>
        )
    }

    render() {
        return (
            <Accordion>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle as={Button} variant="link" eventKey="0">
                            Ustawienia wyszukiwania
                        </Accordion.Toggle>
                    </Card.Header>
                    <Accordion.Collapse eventKey="0">
                        <Card.Body>
                            {this.renderRangeSlider()}
                            <div style={{marginTop: "3rem"}}></div>
                            {this.renderLimitSlider()}
                        </Card.Body>
                    </Accordion.Collapse>
                </Card>
            </Accordion>
        )
    }
}

function mapStateToProps(state) {
    return {
        range: state.nearestParams.range,
        limit: state.nearestParams.limit,
        isLoading: state.isLoading
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onNearestParamsChanged: (nearestParams) => dispatch(NearestParamsActions.paramsRefreshed(nearestParams)),
        onRefreshStarted: () => dispatch(SpinnerActions.refreshStarted()),
        onRefreshStopped: () => dispatch(SpinnerActions.refreshStopped())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(NearestParams)