import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {FormGroup, Input, Label} from 'reactstrap';

import 'react-calendar/dist/Calendar.css';
import CollapsibleBarChart from './collapsible-barchart';
import DeviceTableClient from './device-table-client';

export default class CollapsibleTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            operation: localStorage.getItem("operation"),
            tableData: this.props.data,
            errorStatus: 0,
            error: null,
            date: null
        };
        this.handleChange = this.handleChange.bind(this);
        this.reloadHandler = this.reloadHandler.bind(this);
    }

    reloadHandler() {
        this.setState({
            isLoaded: false
        })
        console.log('isLoaded: ', this.state.isLoaded)
    }

    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;
        console.log(value);
        this.setState({
            date: value,
        });
        if (this.state.date === null) {
            this.setState({isLoaded: true})
        } else {
            console.log("not our first click: ")
            this.forceUpdate()
        }

    };

    render() {
        const tableData = this.state.tableData;
        return (
            <div>
                {this.state.operation==="date" &&<FormGroup id='date'>
                    <Label for='dateField'>Date:</Label>
                    <Input name='date' type="date" id='dateField' onChange={this.handleChange}/>
                </FormGroup>}
                <TableContainer component={Paper}>


                    <Table aria-label="collapsible table">
                        <TableHead>
                            <TableRow>
                                <TableCell/>
                                <TableCell>Device</TableCell>
                                <TableCell align="right">address</TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.date !== null && this.state.operation ==="date" && this.state.isLoaded && tableData.map((row) => (
                                <CollapsibleBarChart isLoaded={this.state.isLoaded} key={row.description} row={row}
                                                     date={this.state.date}/>
                            ))}

                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        )
    }

}


