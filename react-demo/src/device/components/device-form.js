import React from 'react';
import validate from "./validators/device-validators";
import Button from "react-bootstrap/Button";
import * as API_DEVICES from "../api/device-api";
import {Col, Row} from "reactstrap";
import {FormGroup, Input, Label} from 'reactstrap';
import APIResponseErrorMessage from '../../commons/errorhandling/api-response-error-message';


class DeviceForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.operation = this.props.operation;
        this.renderInsert = this.renderInsert.bind(this);
        this.renderDelete = this.renderDelete.bind(this);
        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                description: {
                    value: '',
                    placeholder: 'What is device description ?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true,
                        descriptionValidator: true
                    }
                },
                maxHourCons: {
                    value: 0.0,
                    placeholder: 'Max energy consumption per hour ?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        doubleValidator: true
                    }
                },
                address: {
                    value: '',
                    placeholder: 'Address where device is located...',
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
        for (let updatedFormElementdescription in updatedControls) {
            formIsValid = updatedControls[updatedFormElementdescription].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    updateDevice(device) {

        return API_DEVICES.updateDevice(device, (result, status, err) => {
            if (result !== null && status === 200) {

                console.log("Successfully updated device with id: " + result.id);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        })
    }

    registerDevice(device) {
        return API_DEVICES.postDevice(device, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully inserted device with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    deleteDevice(description) {
        return API_DEVICES.deleteDevice(description, (result, status, err) => {
            if (result !== null && status === 202) {

                console.log("Successfully deleted device with id: " + result);
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
        let device = {
            description: this.state.formControls.description.value,
            maxHourlyEnergyCons: this.state.formControls.maxHourCons.value,
            address: this.state.formControls.address.value,
        };


        if (this.operation === "insert")
            this.registerDevice(device);
        if (this.operation === "modify")
            this.updateDevice(device);
        if (this.operation === "delete")
            this.deleteDevice(device.description);
    }

    renderInsert() {
        return (
            <div>
                <FormGroup id='description'>
                    <Label for='descriptionField'>description:</Label>
                    <Input name='description' id='descriptionField'
                           placeholder={this.state.formControls.description.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.description.value}
                           touched={this.state.formControls.description.touched ? 1 : 0}
                           valid={this.state.formControls.description.valid}
                           required
                    />
                    {this.state.formControls.description.touched && !this.state.formControls.description.valid &&
                    <div className={"error-messaddress row"}>* description must have at least 3 characters and a-z
                        characters</div>}
                </FormGroup>

                <FormGroup id='maxHourCons'>
                    <Label for='maxHourConsField'>Maximum Hourly Consumption Energy in Watts:</Label>
                    <Input name='maxHourCons' id='maxHourConsField'
                           placeholder={this.state.formControls.maxHourCons.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.maxHourCons.value}
                           touched={this.state.formControls.maxHourCons.touched ? 1 : 0}
                           valid={this.state.formControls.maxHourCons.valid}
                           required
                    />
                    {this.state.formControls.maxHourCons.touched && !this.state.formControls.maxHourCons.valid &&
                    <div className={"error-messaddress"}>* maximum Hourly consumption must be a positive double</div>}
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
            </div>
        )
    }

    renderDelete() {
        return (
            <div>
                <FormGroup id='description'>
                    <Label for='descriptionField'>description:</Label>
                    <Input name='description' id='descriptionField'
                           placeholder={this.state.formControls.description.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.description.value}
                           touched={this.state.formControls.description.touched ? 1 : 0}
                           valid={this.state.formControls.description.valid}
                           required
                    />
                    {this.state.formControls.description.touched && !this.state.formControls.description.valid &&
                    <div className={"error-messaddress row"}>* description must have at least 3 characters</div>}
                </FormGroup>
            </div>
        )
    }

    render() {
        console.log(this.operation)
        return (
            <div>
                {this.operation === "insert" && <this.renderInsert/>}
                {this.operation === "delete" && <this.renderDelete/>}
                <Row>
                    <Col sm={{size: '4', offset: 8}}>

                        {this.operation === "insert" &&
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>
                            Submit</Button>}
                        {this.operation === "delete" &&
                        <Button type={"submit"} onClick={this.handleSubmit}>Submit</Button>}
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

export default DeviceForm;
