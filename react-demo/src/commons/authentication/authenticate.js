import React from "react";
import './authenticate.css';
import Card from '../cards/Card';
import '../cards/grid.css';
import '../cards/normalize.css';
import axios from "axios";
import {HOST} from '../hosts';

const endpoint = {
    auth: '/auth'
};


export default class Authenticate extends React.Component {

    constructor(props) {
        super(props);
        this.handleClient = this.handleClient.bind(this);
        this.handleAdministrator = this.handleAdministrator.bind(this);
    }

    handleClient = () => {
        console.log("Handle client: ", HOST.backend_api + endpoint.auth + "/login")
        let authDTO = {
            username: 'client',
            password: 'password'
        };

        axios.post(HOST.backend_api + endpoint.auth + "/login", authDTO)
            .then(response => {
                if (response !== null && (response.status === 200 || response.status === 201)) {
                    localStorage.setItem('access_token', response.data.access_token);
                    localStorage.setItem('user', 'client');


                    console.log("Successfully logged person");
                    this.props.history.push('/home');
                }
            })
            .catch(error => {
                console.log(error);
                this.setState(({
                    errorStatus: error.status,
                    error: error
                }));
            });

    }

    handleAdministrator = () => {

        let authDTO = {
            username: 'admin',
            password: 'password'
        };

        axios.post(HOST.backend_api + endpoint.auth + "/login", authDTO)
            .then(response => {
                if (response !== null && (response.status === 200 || response.status === 201)) {
                    localStorage.setItem('access_token', response.data.access_token);
                    localStorage.setItem('user', 'admin');


                    console.log("Successfully logged person");
                    this.props.history.push('/home');

                }
            })
            .catch(error => {
                console.log(error);
                this.setState({
                    errorStatus: error.status,
                    error: error
                });
            });


    }
    
    render() {

        return (
            <div className="auth">
                <header className="auth-header">
                    <Card
                        title="Client"
                        imageUrl="https://www.iconpacks.net/icons/1/free-user-group-icon-296-thumb.png"
                        handleAction={this.handleClient}
                        action="go to page"
                    />

                    <Card
                        title="Administrator"
                        imageUrl="https://www.iconpacks.net/icons/1/free-user-group-icon-296-thumb.png"
                        handleAction={this.handleAdministrator}
                        id="teacher"
                        action="go to page"
                    />

                </header>
            </div>
        );
    }
}