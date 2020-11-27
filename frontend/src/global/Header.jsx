import React from 'react'
import {Navbar, Nav} from "react-bootstrap"
import {Link} from "react-router-dom"

export default class Header extends React.Component {
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="#timetables"/>
                <Nav className="mr-auto">
                    <Nav.Link as={Link} to="/">Ulubione</Nav.Link>
                    <Nav.Link as={Link} to="/search">Wyszukaj</Nav.Link>
                    <Nav.Link as={Link} to="/nearest">Przystanki w pobliżu</Nav.Link>
                </Nav>
            </Navbar>
        )
    }
}