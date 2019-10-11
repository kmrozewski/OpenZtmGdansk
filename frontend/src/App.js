import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import AppRouter from "./AppRouter"


export default class App extends React.Component {
    render() {
        return (
            <BrowserRouter>
                <AppRouter/>
            </BrowserRouter>
        );
    }
}