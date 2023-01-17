import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Card,
    CardHeader,
    Col,
    Row
} from 'reactstrap';

import * as API_CLIENT from "./api/client-api";

import CollapsibleTable from './components/client-device-table';
import NavigationBar from "../navigation-bar";
import DeviceTableClient from './components/device-table-client';

import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { HOST } from '../commons/hosts';
export default class ClientContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            operation: this.props.operation,
            tableData: [],
            errorStatus: 0,
            error: null,
            isLoaded: false,
            message : null
        };
        localStorage.setItem("operation",this.props.operation)
        localStorage.setItem("error","false")
    }

    async componentDidMount() {
        this.fetchClientDevices();
        let self = this;
        if(localStorage.getItem('user') === 'client'){
        
            var socket = new SockJS(HOST.rabbit_api);
            var stompClient = Stomp.over(socket);
            stompClient.connect({login : 'guest', passcode :'guest'},
            function(){
                // successful connection
                console.log('[home]: connected to socket')

                var subscription = stompClient.subscribe('/topic/ds2020', function(payload){
                    // message received from subscription
                    var message = JSON.parse(payload.body);
                    self.setState({message : message})
                });
            }
            );

        }
               
    }

    async componentDidUpdate(){
        let message = this.state.message;
        if(message != null){
            let data = this.state.tableData;
            let deviceID = message.deviceID;
            let measurementValue = message.measurementValue;
            for(let i = 0;i < data.length;i++ ){

                if(data[i].id.toString() == deviceID){
                    console.log('[client-container] : ',data[i].id)
                    console.log("[client-container] : ",message.deviceID)
                    localStorage.setItem('message'," Device " + deviceID.toString() +" overflow with value "  + measurementValue.toString());
                    localStorage.setItem('error',"true")
                    this.setState({message:null})
                    await new Promise(r => setTimeout(r, 4000));
                    localStorage.setItem('error',"false")
                    console.log(localStorage.getItem('message'))
                }
            }
        }
      
    }
    fetchClientDevices() {

        return API_CLIENT.getMyDevices((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    render() {
        return (
            <div>
                <NavigationBar user={localStorage.getItem('user')}/>
                <CardHeader>
                    <strong>Device visualisation</strong>
                </CardHeader>
                <Card>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.operation==="date" && this.state.isLoaded && <CollapsibleTable 
                                tableData={this.state.tableData} 
                                />}
                            
                            {this.state.operation==="list" && this.state.isLoaded && <DeviceTableClient 
                                tableData={this.state.tableData} 
                                />}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />}
                        </Col>
                    </Row>
                </Card>
                {localStorage.getItem("error")== "true"  &&
                    <footer className='error-text'>{localStorage.getItem('message')} </footer>
                }
            </div>
        )

    }
}


