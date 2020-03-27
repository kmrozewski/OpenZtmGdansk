import React from 'react'
import 'react-bootstrap-typeahead/css/Typeahead.css'
import {Typeahead} from 'react-bootstrap-typeahead'
import {Redirect} from 'react-router'
import {stops as stopNames} from '../stop/stops'

export const isStopNameValid = (stopName) => stopNames.includes(stopName)

export default class Search extends React.Component {

	constructor() {
		super()

		this.state = {
			stopName: "",
			redirect: false
		}
	}

	stopSearched = (selected) => {
		if (isStopNameValid(selected[0])) {
			this.setState({
				stopName: selected,
				redirect: true
			})
		}
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
	    			options={stopNames}
	    			placeholder="Wyszukaj przystanek..."
	    			onChange={this.stopSearched}
	    		/>
    	)
    }
}