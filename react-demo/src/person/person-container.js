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
import PersonForm from "./components/person-form";
import * as API_USERS from "./api/user-api"
import UserTable from "./components/person-table";
import "./components/person-container.css";
import NavigationBar from '../navigation-bar';

class UserContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleForm2 = this.toggleForm2.bind(this);
        this.toggleForm3 = this.toggleForm3.bind(this);
        this.toggleForm4 = this.toggleForm4.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            selected2: false,
            selected3: false,
            selected4: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            operation: "",
            errorStatus: 0,
            error: null
        };

    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

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

        this.setState({selected: !this.state.selected, operation : "insert"});
    }

    toggleForm2() {

        this.setState({selected2: !this.state.selected2,operation : "modify"});
    }

    toggleForm3() {
        this.setState({selected3: !this.state.selected3, operation : "delete"})
    }

    toggleForm4() {
        this.setState({selected4: !this.state.selected4, operation : "map"})
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        if (this.operation === "insert") this.toggleForm();
        if (this.operation === "modify") this.toggleForm2();
        if (this.operation === "delete") this.toggleForm3();
        if (this.operation === "map") this.toggleForm4();
        this.fetchUsers();
    }

    render() {
        return (
            <div>
                <NavigationBar user={localStorage.getItem('user')}/>
                <CardHeader>
                    <strong>User Management</strong>
                </CardHeader>
                <Card>
                    <div className="user-tasks">
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm}>Add User</Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm2}>Modify User</Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm3}>Delete User</Button>
                            </Col>
                        </Row>

                        <Row>
                            <Col sm={{size: '8', offset: 1}}>
                                <Button color="primary" onClick={this.toggleForm4}>Map Device to User</Button>
                            </Col>
                        </Row>

                    </div>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <UserTable tableData={this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />}
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm} className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}>Add User:</ModalHeader>
                    <ModalBody>
                        <PersonForm operation={"insert"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selected2} toggle={this.toggleForm2} className={this.props.className}
                       size="lg">
                    <ModalHeader toggle={this.toggleForm2}>Add User:</ModalHeader>
                    <ModalBody>
                        <PersonForm operation={"modify"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selected3} toggle={this.toggleForm3} className={this.props.className}
                       size="lg">
                    <ModalHeader toggle={this.toggleForm3}>Delete User:</ModalHeader>
                    <ModalBody>
                        <PersonForm operation={"delete"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>
                <Modal isOpen={this.state.selected4} toggle={this.toggleForm4} className={this.props.className}
                       size="lg">
                    <ModalHeader toggle={this.toggleForm4}>Map User to Device:</ModalHeader>
                    <ModalBody>
                        <PersonForm operation={"map"} reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>


            </div>
        )

    }
}


export default UserContainer;
