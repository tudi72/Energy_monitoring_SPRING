import React from 'react'
import logo from './commons/images/icon.png';
import "./navigation-bar.css";
import {
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavLink,
    NavItem,
    UncontrolledDropdown
} from 'reactstrap';

const textStyle = {
    color: 'black',
    textDecoration: 'none'
};

const NavigationBar = () => (

    <div>
        <Navbar color="white" light expand="md">
            <NavbarBrand href="/">
                <img src={logo} width={"50"}
                     height={"35"}/>
            </NavbarBrand>

            {localStorage.getItem('user') === 'client' &&
            <Nav className="mr-auto" navbar>
                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle style={textStyle} nav caret>
                        Menu
                    </DropdownToggle>
                    <DropdownMenu right>
                        <DropdownItem>
                            <NavLink href="/clientDevicesByDate">Consumption</NavLink>
                            <NavLink href="/clientDevices">My devices</NavLink>
                        </DropdownItem>

                    </DropdownMenu>
                </UncontrolledDropdown>
            </Nav>}

            {localStorage.getItem('user') === 'admin' &&
            <Nav className="mr-auto" navbar>

                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle style={textStyle} nav caret>
                        Menu
                    </DropdownToggle>
                    <DropdownMenu right>
                        <DropdownItem>
                            <NavLink href="/person">Persons</NavLink>
                            <NavLink href="/devices">Devices</NavLink>
                        </DropdownItem>

                    </DropdownMenu>
                </UncontrolledDropdown>
            </Nav>}
        </Navbar>
    </div>
);

export default NavigationBar
