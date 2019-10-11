import React from 'react'
import {Switch, Redirect, Route} from 'react-router-dom'
import Timetable from "./Timetable"
import Search from "./Search"
import Error404 from "./Error404"

export default class AppRouter extends React.Component {
    render() {
        return (
            <Switch>
                <Route path="/timetable" component={Timetable}/>
                <Route path="/search" component={Search}/>
                <Redirect path="/" exact to ="/timetable"/>
                <Route component={Error404}/>
            </Switch>
        )
    }
}