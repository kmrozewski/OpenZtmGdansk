import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import AppRouter from "./global/AppRouter"
import Header from "./global/Header"


export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <Header/>
                <AppRouter/>
            </BrowserRouter>
        );
    }
}