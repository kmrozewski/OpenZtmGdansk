import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import AppRouter from "./global/AppRouter"
import Header from "./global/Header"
import Announcements from './announcement/Announcements'


export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Header/>
                <Announcements/>
                <AppRouter/>
            </BrowserRouter>
        );
    }
}