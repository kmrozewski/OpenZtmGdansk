import React from 'react'
import {Navbar, Nav} from "react-bootstrap"
import {Link} from "react-router-dom"

export default class Header extends React.Component {
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="#timetables"/>
                <Nav className="mr-auto">
                    <Nav.Link as={Link} to="/">Timetables</Nav.Link>
                    <Nav.Link as={Link} to="/search">Search</Nav.Link>
                    <Nav.Link as={Link} to="/air">Air quality</Nav.Link>
                </Nav>
            </Navbar>
        )
    }
}