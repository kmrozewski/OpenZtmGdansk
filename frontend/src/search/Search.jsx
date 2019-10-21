import React from 'react'
import {InputGroup, Button, FormControl} from 'react-bootstrap'
import { FaSearch } from "react-icons/fa";
import * as text from '../global/stops.json'

export default class Search extends React.Component {
    render() {
    	console.log(text[0])

        return (
			<InputGroup className="mb-3">
				<InputGroup.Prepend>
					<Button variant="outline-primary">
						<FaSearch/>
					</Button>
				</InputGroup.Prepend>
				<FormControl aria-describedby="basic-addon1" placeholder="Wyszukaj przystanek..." />
			</InputGroup>
        );
    }
}