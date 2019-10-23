import React from 'react'
import {Switch, Redirect, Route} from 'react-router-dom'
import Estimate from "../delay/Estimate"
import Delay from "../delay/Delay"
import Search from "../search/Search"
import Error404 from "./Error404"
import Air from './Air'
import Stop from '../stop/Stop'

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
                <Route path="/stop/:stopName" render={({match}) => (<Stop stopName={match.params.stopName}/>) }/>
                <Redirect path="/" exact to="/timetable"/>
                <Route path="/404" exact component={Error404}/>
                <Route component={Error404}/>
            </Switch>
        )
    }
}