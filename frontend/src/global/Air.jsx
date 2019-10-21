import React from 'react'

export default class Air extends React.PureComponent {
	render() {
		return (
			<React.Fragment>
                <script src="https://airly.eu/map/airly.js" type="text/javascript"></script>
                <iframe id="airly_2033705610" title="airly_2033705610" src="https://airly.eu/map/widget.html#lat=54.35184&lng=18.57205&id=9853&w=280&h=380&m=true&i=true&ah=true&aw=true" style={{width:"100%", border:"none"}}></iframe>
            </React.Fragment>
		)
	}
}