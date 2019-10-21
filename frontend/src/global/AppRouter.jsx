import React from 'react'
import {Switch, Redirect, Route} from 'react-router-dom'
import Estimate from "../delay/Estimate"
import Delay from "../delay/Delay"
import Search from "../search/Search"
import Error404 from "./Error404"
import Air from './Air'

export default class AppRouter extends React.Component {
    render() {
        return (
            <Switch>
                <Route path="/timetable" component={Estimate}/>
                <Route path="/search" exact component={Search}/>
                <Route path={'/search/:stopId'}
                           render={({match}) => (
                               <Delay stopIds={[parseInt(match.params.stopId, 10)]}/>
                           )}/>
                <Route path="/air" component={Air}/>
                <Redirect path="/" exact to ="/timetable"/>
                <Route component={Error404}/>
            </Switch>
        )
    }
}