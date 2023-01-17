import React from 'react';
import validate from "./validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API_USERS from "../api/user-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';
import connect from '../../commons/websockets/connection-handler';

class PersonForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.operation = this.props.operation;
        this.renderInsert = this.renderInsert.bind(this);
        this.renderDelete = this.renderDelete.bind(this);
        this.renderMap = this.renderMap.bind(this);
        this.renderModify = this.renderModify.bind(this);
        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,
            formIsValid2: false,
            formControls2: {
                name: {
                    value: '',
                    placeholder: 'What is the user name?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                device: {
                    value: '',
                    placeholder: 'What is the device name?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                }
            },
            formControls: {
                name: {
                    value: '',
                    placeholder: 'What is your name?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                email: {
                    value: '',
                    placeholder: 'Email...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        emailValidator: true
                    }
                },
                age: {
                    value: '',
                    placeholder: 'Age...',
                    valid: false,
                    touched: false,
                },
                address: {
                    value: '',
                    placeholder: 'Cluj, Zorilor, Str. Lalelelor 21...',
                    valid: false,
                    touched: false,
                },
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }

    handleChange2 = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls2 = this.state.formControls2;
        const updatedFormElement2 = updatedControls2[name];


        updatedFormElement2.value = value;
        updatedFormElement2.touched = true;
        updatedFormElement2.valid = validate(value, updatedFormElement2.validationRules);
        updatedControls2[name] = updatedFormElement2;

        let formIsValid2 = true;
        for (let updatedFormElementName2 in updatedControls2) {
            formIsValid2 = updatedControls2[updatedFormElementName2].valid && formIsValid2;
        }

        this.setState({
            formControls2: updatedControls2,
            formIsValid2: formIsValid2
        });


    }

    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];


        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    updateUser(person) {

        return API_USERS.updateUser(person, (result, status, err) => {
            if (result !== null && status === 200) {

                console.log("Successfully updated person with id: " + result.id);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        })
    }

    registerPerson(person) {
        return API_USERS.postUser(person, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully inserted person with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    deleteUser(name) {
        return API_USERS.deleteUser(name, (result, status, err) => {
            if (result !== null && status === 202) {

                console.log("Successfully deleted person with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        })
        this.toggleForm();
    }

    mapDeviceToUser(obj) {
        return API_USERS.mapDeviceToUser(obj, (result, status, err) => {
            if (result !== null && (status === 202 || status === 200 || status === 201)) {

                console.log("[person-form] : map (user,device ) --> device : "+ result);
            //    in here the administrator must connect to socket and send a message to socket controller
               
                connect(result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        })
    }

    handleSubmit() {
        console.log("operation",this.operation)

        let person = {
            name: this.state.formControls.name.value,
            username: this.state.formControls.email.value,
            age: this.state.formControls.age.value,
            address: this.state.formControls.address.value
        };
        
        let obj = {
            name: this.state.formControls2.name.value,
            device: this.state.formControls2.device.value
        }
        if (this.operation === "insert")
            this.registerPerson(person);
        if (this.operation === "modify")
            this.updateUser(person);
        if (this.operation === "delete")
            this.deleteUser(person.name);
        if (this.operation === "map")
            this.mapDeviceToUser(obj);

        this.toggleForm();

    }

    renderModify() {
        return (
            <div>
                <FormGroup id='name'>
                    <Label for='nameField'>Name:</Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls.name.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.name.value}
                           touched={this.state.formControls.name.touched ? 1 : 0}
                    />
                    {this.state.formControls.name.touched && !this.state.formControls.name.valid &&
                    <div className={"error-message row"}>* Name must have at least 3 characters</div>}
                </FormGroup>

                <FormGroup id='email'>
                    <Label for='emailField'>Email:</Label>
                    <Input name='email' id='emailField' placeholder={this.state.formControls.email.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.email.value}
                           touched={this.state.formControls.email.touched ? 1 : 0}
                    />
                    {this.state.formControls.email.touched && !this.state.formControls.email.valid &&
                    <div className={"error-message"}>* Email must have a valid format</div>}
                </FormGroup>

                <FormGroup id='address'>
                    <Label for='addressField'>Address:</Label>
                    <Input name='address' id='addressField' placeholder={this.state.formControls.address.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.address.value}
                           touched={this.state.formControls.address.touched ? 1 : 0}
                    />
                </FormGroup>

                <FormGroup id='age'>
                    <Label for='ageField'>Age:</Label>
                    <Input name='age' id='ageField' placeholder={this.state.formControls.age.placeholder}
                           min={0} max={100} type="number"
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.age.value}
                           touched={this.state.formControls.age.touched ? 1 : 0}
                    />
                </FormGroup>
            </div>
        )
    }

    renderInsert() {
        return (
            <div>
                <FormGroup id='name'>
                    <Label for='nameField'>Name:</Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls.name.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.name.value}
                           touched={this.state.formControls.name.touched ? 1 : 0}
                           valid={this.state.formControls.name.valid}
                           required
                    />
                    {this.state.formControls.name.touched && !this.state.formControls.name.valid &&
                    <div className={"error-message row"}>* Name must have at least 3 characters</div>}
                </FormGroup>

                <FormGroup id='email'>
                    <Label for='emailField'>Email:</Label>
                    <Input name='email' id='emailField' placeholder={this.state.formControls.email.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.email.value}
                           touched={this.state.formControls.email.touched ? 1 : 0}
                           valid={this.state.formControls.email.valid}
                           required
                    />
                    {this.state.formControls.email.touched && !this.state.formControls.email.valid &&
                    <div className={"error-message"}>* Email must have a valid format</div>}
                </FormGroup>

                <FormGroup id='address'>
                    <Label for='addressField'>Address:</Label>
                    <Input name='address' id='addressField' placeholder={this.state.formControls.address.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.address.value}
                           touched={this.state.formControls.address.touched ? 1 : 0}
                           valid={this.state.formControls.address.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='age'>
                    <Label for='ageField'>Age:</Label>
                    <Input name='age' id='ageField' placeholder={this.state.formControls.age.placeholder}
                           min={0} max={100} type="number"
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.age.value}
                           touched={this.state.formControls.age.touched ? 1 : 0}
                           valid={this.state.formControls.age.valid}
                           required
                    />
                </FormGroup>
            </div>
        )
    }

    renderDelete() {
        return (
            <div>
                <FormGroup id='name'>
                    <Label for='nameField'>Name:</Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls.name.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.name.value}
                           touched={this.state.formControls.name.touched ? 1 : 0}
                           valid={this.state.formControls.name.valid}
                           required
                    />
                    {this.state.formControls.name.touched && !this.state.formControls.name.valid &&
                    <div className={"error-message row"}>* Name must have at least 3 characters</div>}
                </FormGroup>
            </div>
        )
    }

    renderMap() {
        return (
            <div>

                <FormGroup id='name'>
                    <Label for='nameField'>Name:</Label>
                    <Input name='name' id='nameField' placeholder={this.state.formControls2.name.placeholder}
                           onChange={this.handleChange2}
                           defaultValue={this.state.formControls2.name.value}
                           touched={this.state.formControls2.name.touched ? 1 : 0}
                           valid={this.state.formControls2.name.valid}
                           required
                    />
                    {this.state.formControls2.name.touched && !this.state.formControls2.name.valid &&
                    <div className={"error-message row"}>* Device Name must have at least 3 characters</div>}
                </FormGroup>

                <FormGroup id='device'>
                    <Label for='deviceField'>Device:</Label>
                    <Input name='device' id='deviceField' placeholder={this.state.formControls2.device.placeholder}
                           onChange={this.handleChange2}
                           defaultValue={this.state.formControls2.device.value}
                           touched={this.state.formControls2.device.touched ? 1 : 0}
                           valid={this.state.formControls2.device.valid}
                           required
                    />
                    {this.state.formControls2.device.touched && !this.state.formControls2.device.valid &&
                    <div className={"error-message row"}>* Name must have at least 3 characters</div>}
                </FormGroup>

            </div>
        )
    }

    render() {
        return (
            <div>
                {this.operation === "insert" && <this.renderInsert/>}
                {this.operation === "map" && <this.renderMap/>}
                {this.operation === "delete" && <this.renderDelete/>}
                {this.operation === "modify" && <this.renderModify/>}
                <Row>
                    <Col sm={{size: '4', offset: 8}}>

                        {(this.operation === "insert") &&
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>
                            Submit</Button>}
                            {this.operation === "delete" &&
                        <Button type={"submit"}  onClick={this.handleSubmit}>
                            Submit</Button>}

                            {this.operation === "modify" &&
                        <Button type={"submit"}  onClick={this.handleSubmit}>
                            Submit</Button>}

                        {this.operation === "map" &&
                        <Button type={"submit"} disabled={!this.state.formIsValid2} onClick={this.handleSubmit}>
                            Submit</Button>}

                    </Col>
                </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        );
    }
}

export default PersonForm;
