import React from "react";
import Table from "../../commons/tables/table";


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

class DeviceTableClient extends React.Component {

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            tableData: this.props.tableData
        };

    }

    render() {
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default DeviceTableClient;