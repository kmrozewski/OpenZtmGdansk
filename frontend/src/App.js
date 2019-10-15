import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import {Provider} from "react-redux";
import {createStore} from "redux";
import 'bootstrap/dist/css/bootstrap.min.css';
import AppRouter from "./global/AppRouter"
import Header from "./global/Header"
import Announcements from './announcement/Announcements'
import rootReducer from './state/rootReducer'
import {getAnnouncements} from './global/api'
import {announcementsRefreshed} from './announcement/actions'

const store = createStore(rootReducer);

export default class App extends React.Component {

    async componentDidMount() {
        const announcements = await getAnnouncements()
        store.dispatch(announcementsRefreshed(announcements))
    }

    render() {
        return (
        	<Provider store={store}>
	            <BrowserRouter>
	                <Header/>
	                <Announcements/>
	                <AppRouter/>
	            </BrowserRouter>
            </Provider>
        );
    }
}