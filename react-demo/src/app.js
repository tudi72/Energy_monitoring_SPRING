import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
// import NavigationBar from './navigation-bar'
import Home from './home/home';
import PersonContainer from './person/person-container'
import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import DeviceContainer from './device/device-container';
import ClientContainer from './client/client-container';
import Login from './commons/authentication/auth-smth';
import Chat from './chat';
import ChatMain from './chat';


class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message  : "",
            error : false
        }
    }
    render() {

        return (
            <div className={styles.back}>
                <Router>
                    <div>
                        <Switch>

                            <Route
                                exact path='/'
                                // render={(props) => <Authenticate {...props} authed={true}/>}
                                render={(props) => <Login {...props} authed={true}/>}
                                />
                            
                            <Route exact path='/chat' 
                                    render={(props) => <ChatMain {...props} />} />
                            

                            <Route
                                exact path='/devices'
                                render={(props) => <DeviceContainer {...props} authed={true}/>}
                            />

                            <Route
                                exact path='/clientDevicesByDate'
                                render={(props) => <ClientContainer {...props} operation ={"date"} authed={true}/>}
                            />
                            <Route
                                exact path='/clientDevices'
                                render={(props) => <ClientContainer {...props} operation={"list"} authed={true}/>}
                            />

                            <Route
                                exact path='/home'
                                render={(props) => <Home {...props} authed={true}/>}
                            />
                            <Route
                                exact path='/person'
                                render={(props) => <PersonContainer {...props} authed={true}/>}
                            />

                            {/*Error*/}
                            <Route
                                exact path='/error'
                                render={() => <ErrorPage/>}
                            />

                            <Route render={() => <ErrorPage/>}/>
                        </Switch>
                    </div>
                </Router>

            </div>
        )
    };
}

export default App
