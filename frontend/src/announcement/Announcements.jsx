import React from 'react'
import {connect} from "react-redux";
import {Accordion, Card, Button, Badge} from 'react-bootstrap'

export class Announcements extends React.Component {
	render() {
		return (
			<Accordion>
				<Card>
				    <Card.Header>
				      <Accordion.Toggle as={Button} variant="link" eventKey="0">
						<Badge variant="secondary">{this.props.announcements.length}</Badge> Komunikaty
						<span className="sr-only">unread messages</span>
				      </Accordion.Toggle>
				    </Card.Header>
				    <Accordion.Collapse eventKey="0">
				      <Card.Body>
				      	{this.props.announcements.length === 0
				      		? <Card.Text>Brak komunikatów</Card.Text>
				      		: this.props.announcements.map((announcement, index) => <Card.Text key={index}>{announcement}</Card.Text>)}
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