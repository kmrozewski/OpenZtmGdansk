import React from 'react'
import 'react-bootstrap-typeahead/css/Typeahead.css'
import {Typeahead} from 'react-bootstrap-typeahead'
import {Redirect} from 'react-router'
import {connect} from "react-redux";

export const isStopNameValid = (stopName, stopNames) => stopNames.includes(stopName)

class Search extends React.Component {

	constructor() {
		super()

		this.state = {
			stopName: "",
			redirect: false
		}
	}

	stopSearched = (selected) => {
		if (isStopNameValid(selected[0], this.props.stopNames)) {
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
	    			options={this.props.stopNames}
	    			placeholder="Wyszukaj przystanek..."
	    			onChange={this.stopSearched}
	    		/>
    	)
    }
}

function mapStateToProps(state) {
	return {
		stopNames: state.stopNames
	}
}

export default connect(mapStateToProps)(Search)