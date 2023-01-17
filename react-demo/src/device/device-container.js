import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
import * as API_DEVICES from "./api/device-api";
import DeviceTable from './components/device-table';
import DeviceForm from './components/device-form';
import "./components/device-container.css";
import NavigationBar from '../navigation-bar';

import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { HOST } from '../commons/hosts';

export default class DeviceContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleForm2 = this.toggleForm2.bind(this);
        this.toggleForm3 = this.toggleForm3.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            selected2: false,
            selected3: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            operation: "",
            error: null,
        };

    }

    componentDidMount() {
        this.fetchDevices();
    }


    fetchDevices() {
        return API_DEVICES.getDevices((result, status, err) => {

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

    toggleForm() {

        this.setState({selected: !this.state.selected,operation: "insert"});
    }

    toggleForm2() {

        this.setState({selected2: !this.state.selected2,operation: "modify"});
    }

    toggleForm3() {
        this.setState({selected3: !this.state.selected3,operation: "delete"})
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        if (this.operation === "insert") this.toggleForm();
        if (this.operation === "modify") this.toggleForm2();
        if (this.operation === "delete") this.toggleForm3();
        this.fetchDevices();
    }

    render() {
        return (
            <div>
                <NavigationBar user={localStorage.getItem('user')}/>
                <CardHeader>
                    <strong>Device Management</strong>
                </CardHeader>
                <Card>
                    <div className="user-tasks">
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm}>Add Device</Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm2}>Modify Device</Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm3}>Delete Device</Button>
                            </Col>
                        </Row>

                    </div>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DeviceTable tableData={this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />}
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm} className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}>Insert device:</ModalHeader>
                    <ModalBody>
                        <DeviceForm operation={"insert"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selected2} toggle={this.toggleForm2} className={this.props.className}
                       size="lg">
                    <ModalHeader toggle={this.toggleForm2}>Modify device:</ModalHeader>
                    <ModalBody>
                        <DeviceForm operation={"modify"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selected3} toggle={this.toggleForm3} className={this.props.className}
                       size="lg">
                    <ModalHeader toggle={this.toggleForm3}>Delete device:</ModalHeader>
                    <ModalBody>
                        <DeviceForm operation={"delete"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }
}


