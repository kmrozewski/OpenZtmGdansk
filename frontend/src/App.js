import React from 'react';
import {BrowserRouter} from 'react-router-dom'
import {Provider} from "react-redux";
import {createStore} from "redux";
import 'bootstrap/dist/css/bootstrap.min.css';
import AppRouter from "./global/AppRouter"
import Header from "./global/Header"
import Announcements from './announcement/Announcements'
import rootReducer from './state/rootReducer'
import {getAnnouncements, getStopNames, getVehicles} from './global/api'
import {announcementsRefreshed} from './announcement/actions'
import {vehiclesRefreshed} from "./vehicle/actions";
import {stopNamesRefreshed} from "./stop/actions";

const INTERVAL_SECONDS = 10000
const store = createStore(rootReducer);

export default class App extends React.Component {

    updateVehicles = async () => {
        console.log('[App] updating vehicles position')
        const vehicleLocation = await getVehicles()
        store.dispatch(vehiclesRefreshed(vehicleLocation))
    }

    async componentDidMount() {
        const announcements = await getAnnouncements()
        store.dispatch(announcementsRefreshed(announcements))

        const stopNames = await getStopNames()
        store.dispatch(stopNamesRefreshed(stopNames))

        console.log('[App] did mount')
        this.updateVehicles()

        this.interval = setInterval(async() => {
            console.log('[App] setInterval')
            await this.updateVehicles()
        }, INTERVAL_SECONDS)
    }

    async componentWillUnmount() {
        clearInterval(this.interval)
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