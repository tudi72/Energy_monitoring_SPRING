import * as React from 'react';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Tooltip, BarChart, XAxis, YAxis, Legend, CartesianGrid, Bar,} from "recharts";

import * as API_CLIENT from "../api/client-api";

export default class CollapsibleBarChart extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            row: this.props.row,
            date: this.props.date,

            open: false,

            errorStatus: 0,
            error: null
        }
        this.reloadHandler = this.props.reloadHandler;
    }

    async componentDidMount() {

        const params = {
            date: this.state.date,
            description: this.state.row.description
        }
        let bar = [];
        API_CLIENT.getMyDeviceConsumptions(params, (result, status, err) => {
            if (result !== null && status === 200) {
                result = new Map(Object.entries(result));
                result.forEach((value, key) => {
                    let elem = {hour: key, kWh: value}
                    bar.push(elem)

                });
                console.log("[Device.",params.description,"] : ",bar)
                this.setState({
                    data: bar,
                    active: false
                });
                this.forceUpdate();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    render() {
        const row = this.state.row;
        const data = this.state.data;
        return (
            <React.Fragment>
                <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                    <TableCell>
                        <IconButton
                            aria-label="expand row"
                            size="small"
                            onClick={() => (this.setState({open: !this.state.open, active: !this.state.active}))}

                        >

                            {this.state.open ?
                                <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24">
                                    <path d="M0 0h24v24H0V0z" fill="none"/>
                                    <path d="M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6 1.41-1.41z"/>
                                </svg>
                                :
                                <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24">
                                    <path d="M0 0h24v24H0z" fill="none"/>
                                    <path d="M7.41 15.41L12 10.83l4.59 4.58L18 14l-6-6-6 6z"/>
                                </svg>
                            }
                        </IconButton>
                    </TableCell>
                    <TableCell component="th" scope="row">{row.description}</TableCell>
                    <TableCell align="right">{row.address}</TableCell>

                </TableRow>
                <TableRow>
                    {this.state.open === true &&
                    <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                        <Collapse in={this.state.open} timeout="auto" unmountOnExit>

                            <BarChart width={500} height={300} data={data} barSize={20}
                                      margin={{top: 5, right: 30, left: 80, bottom: 5,}}>
                                <XAxis dataKey="hour" scale="point" padding={{left: 10, right: 10}}/>
                                <YAxis/>
                                <Tooltip/>
                                <Legend/>
                                <CartesianGrid strokeDasharray="3 3"/>
                                <Bar dataKey="kWh" fill="#8884d8" background={{fill: "#eee"}}/>
                            </BarChart>

                        </Collapse>
                    </TableCell>}
                </TableRow>
            </React.Fragment>
        );
    }
}