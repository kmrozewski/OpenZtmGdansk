import React from 'react'
import {Switch, Redirect, Route} from 'react-router-dom'
import Delay from "../delay/Delay"
import Search from "../search/Search"
import Error404 from "./Error404"

export default class AppRouter extends React.Component {
    render() {
        return (
            <Switch>
                <Route path="/timetable" component={Delay}/>
                <Route path="/search" component={Search}/>
                <Redirect path="/" exact to ="/timetable"/>
                <Route component={Error404}/>
            </Switch>
        )
    }
}