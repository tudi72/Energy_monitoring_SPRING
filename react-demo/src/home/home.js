import React from 'react';
import BackgroundImg from '../commons/images/background_green.jpg';
import {Jumbotron} from 'react-bootstrap';
import NavigationBar from '../navigation-bar';

import '../home/home.css';
const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'auto',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "800px",
    backgroundImage: `url(${BackgroundImg})`
};

class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message : null
        }
    }


    render() {
        return (
            <div>
        
                <NavigationBar user={localStorage.getItem('user')}/>
                <Jumbotron fluid style={backgroundStyle}>
                </Jumbotron>

            </div>
        )
    };
}

export default Home
