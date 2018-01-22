import React, {Component} from "react";
import {Navbar} from "react-bootstrap";
import StockList from './StockList'

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {token: null};
    }

    componentWillMount() {
        const user = {
            username: "admin",
            password: "password"
        };

        fetch("http://localhost:5000/token", {
            method: 'post',
            body: JSON.stringify(user)
        }).then(resp => resp.headers.get("Authorization"))
            .then(header => {
                this.setState({token: header});
            });
    }

    render() {
        const jwtData = this.state.token;
        if (!jwtData) return <div>Loading...</div>;

        return (
            <div>
                <Navbar>
                    <Navbar.Header>
                        <Navbar.Brand>
                            Stock Management UI App
                        </Navbar.Brand>
                    </Navbar.Header>
                </Navbar>
                <StockList token={this.state.token}/>
            </div>
        )
    }
}

export default App;
