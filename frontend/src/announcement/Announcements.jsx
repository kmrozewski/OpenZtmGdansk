import React from 'react'
import {Accordion, Card, Button, Badge} from 'react-bootstrap'
import {getAnnouncements} from '../global/api'

export default class Announcements extends React.Component {

	constructor() {
		super()

		this.state = {
			announcements: []
		}
	}

	async componentDidMount() {
		const result = await getAnnouncements()

		this.setState({
			isLoading: result.isLoading,
			announcements: result.announcements
		})
	}

	render() {
		return (
			<Accordion defaultActivateKey="0">
				<Card>
				    <Card.Header>
				      <Accordion.Toggle as={Button} variant="link" eventKey="0">
						<Badge variant="secondary">{this.state.announcements.length}</Badge> Komunikaty
						<span className="sr-only">unread messages</span>
				      </Accordion.Toggle>
				    </Card.Header>
				    <Accordion.Collapse eventKey="0">
				      <Card.Body>
				      	{this.state.announcements.map((announcement, index) => <Card.Text key={index}>{announcement}</Card.Text>)}
				      </Card.Body>
				    </Accordion.Collapse>
				</Card>
			</Accordion>
		)
	}
}