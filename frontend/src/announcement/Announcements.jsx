import React from 'react'
import {connect} from "react-redux";
import {Accordion, Badge, Card} from 'react-bootstrap'
import Alert from "react-bootstrap/Alert";

class Announcements extends React.Component {
    render() {
        return (
            <Accordion>
                <Card>
                    <Accordion.Toggle as={Card.Header} variant="link" eventKey="0">
                        <Badge variant="secondary">{this.props.announcements.length}</Badge> Komunikaty
                        <span className="sr-only">unread messages</span>
                    </Accordion.Toggle>
                    <Accordion.Collapse eventKey="0">
                        <Card.Body>
                            <Alert style={{marginTop: "5px"}} variant="info">Wyniki są automatycznie odświeżane co 10
                                sekund.</Alert>
                            {this.props.announcements.length === 0
                                ? <Card.Text>Brak komunikatów</Card.Text>
                                : this.props.announcements.map((announcement, index) => <Card.Text
                                    key={index}>{announcement}</Card.Text>)}
                        </Card.Body>
                    </Accordion.Collapse>
                </Card>
            </Accordion>
        )
    }
}

function mapStateToProps(state) {
    return {
        announcements: state.announcements
    }
}

export default connect(mapStateToProps)(Announcements)