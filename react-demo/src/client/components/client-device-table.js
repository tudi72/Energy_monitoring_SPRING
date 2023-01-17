import React from "react";
import Table from "../../commons/tables/table";
import CollapsibleTable from "./collapsible-table";


const columns = [
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'maxHourlyEnergyCons',
        accessor: 'maxHourlyEnergyCons',
    },
    {
        Header: 'Address',
        accessor: 'address',
    }
];

const filters = [
    {
        accessor: 'description',
    }
];

class ClientDeviceTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };

    }

    render() {
        return (
            <CollapsibleTable data={this.props.tableData}/>
        )
    }
}

export default ClientDeviceTable;