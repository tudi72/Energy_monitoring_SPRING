import React, { useState } from "react";
import ReactDOM from "react-dom";
import axios from "axios";
import {HOST} from '../hosts';
import {FormGroup, Input, Label} from 'reactstrap';
import Button from "react-bootstrap/Button";
const endpoint = {
    auth: '/auth'
};


export default class Login extends React.Component {
    // React States
    constructor(props){
        super(props);
        this.state = {
            errorMessages: "",
            isSubmitted: false,
            errorStatus: 0,
            error: null,
            formIsValid: false,
            formControls: {
                username: {
                    value: '',
                    placeholder: 'What is the user name?...',
                },
                password: {
                    value: '',
                    placeholder: 'Password...',
                }
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.submitButton = this.submitButton.bind(this);
    }
    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];


        updatedFormElement.value = value;
        updatedControls[name] = updatedFormElement;


        this.setState({
            formControls: updatedControls,
        });


    };

    submitButton(){
        var querystring = require('querystring');
        axios.post(HOST.backend_api + endpoint.auth + "/login",querystring.stringify({
            username: this.state.formControls.username.value, //gave the values directly for testing
            password: this.state.formControls.password.value,
            }), 
            {
        headers: { 
        "Content-Type": "application/x-www-form-urlencoded",
    }
    })
            .then(response => {
                if (response !== null && (response.status === 200 || response.status === 201)) {
                    localStorage.setItem('access_token', response.data.access_token);
                    if(response.data.role==="ROLE_ADMIN") 
                        localStorage.setItem('user', 'admin');
                    else localStorage.setItem('user','client')


                    console.log("Successfully logged person");
                    this.props.history.push('/home');

                }
            })
            .catch(error => {
                console.log(error);
            });

  
    };
  
    render(){
        return ( 
            <div className="form">

        <FormGroup id='username'>
                    <Label for='usernameField'>username:</Label>
                    <Input name='username' id='usernameField' placeholder={this.state.formControls.username.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.username.value}
                    />
                </FormGroup>
                <FormGroup id='password'>
                    <Label for='passwordField'>Password:</Label>
                    <Input type="password" name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.password.value}
                    />
                </FormGroup>
        <Button type={"submit"}  onClick={this.submitButton}> Submit</Button>
      </div>
        )
    }
  }