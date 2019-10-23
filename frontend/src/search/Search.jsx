import React from 'react'
import 'react-bootstrap-typeahead/css/Typeahead.css'
import {Typeahead} from 'react-bootstrap-typeahead'
import {Redirect} from 'react-router'
import {stops} from '../global/stops'

export default class Search extends React.Component {

	constructor() {
		super()

		this.state = {
			stopName: "",
			redirect: false
		}
	}

	stopSearched = (selected) => {
		this.setState({
			stopName: selected,
			redirect: true
		})
	}

    render() {
    	if (this.state.redirect) {
    		return <Redirect push to={"/stop/" + this.state.stopName} />	
		}

    	return (
    		<Typeahead
    				id="typeahead-search-stop"
	    			caseSensitive={false}
	    			ignoreDiacritics={true}
	    			minLength={3}
	    			options={stops}
	    			placeholder="Wyszukaj przystanek..."
	    			onChange={this.stopSearched}
	    		/>
    	)
    }
}