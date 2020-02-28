import React from 'react'
import {getStopByName} from "../global/api"
import {Link} from "react-router-dom"
import {Accordion, Button, Card} from "react-bootstrap"
import {isStopNameValid} from '../search/Search'

export default class StopCodeList extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            stop: {}
        }
    }

    async componentDidMount() {
        console.log('[StopCodeList] mounted')
        let stop = await getStopByName(this.props.stopName)
        this.setState({stop: stop})
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
        if (this.state.stop.codes && isStopNameValid(this.props.stopName)) {
            return (
                <Accordion defaultActiveKey="0">
                    <Card>
                        <Card.Header>
                            <Accordion.Toggle as={Button} variant="link" eventKey="0">
                                Lista przystanków - {this.props.stopName}
                            </Accordion.Toggle>
                        </Card.Header>
                        <Accordion.Collapse eventKey="0">
                            <Card.Body>
                                <ul>
                                    {this.state.stop.codes.map(this.renderStopCodes)}
                                </ul>
                            </Card.Body>
                        </Accordion.Collapse>
                    </Card>
                </Accordion>
            )
        }

        return (<h3>Nie ma przystanku o nazwie "{this.props.title}"</h3>)
    }

    getPath = (code) => '/stop/' + this.props.stopName + '/' + code + '/'
}
