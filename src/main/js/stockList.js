import React, {Component} from "react";
import {Table} from "react-bootstrap";

class StockList extends Component {

    constructor() {
        super();
        this.state = {
            stocks: []
        }
    }

    componentDidMount() {
        const URL = "http://localhost:5000/api/v1/stocks";
        var myHeaders = new Headers();
        myHeaders.set("Authorization", this.props.token);

        fetch(URL, {
            headers: myHeaders
        }).then(res => res.json()).then(json => {
            this.setState({stocks: json});
        });
    }

    render() {
        const stocksData = this.state.stocks;
        if (!stocksData) return <div/>;

        return (
            <Table striped bordered condensed hover>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Last Update</th>
                </tr>
                </thead>
                <tbody>
                {this.state.stocks.map((stock) =>
                    <Stock key={stock.id} stock={stock}/>
                )}
                </tbody>

            </Table>
        )
    }
}

class Stock extends Component {
    render() {
        return (
            <tr>
                <td>{this.props.stock.id}</td>
                <td>{this.props.stock.name}</td>
                <td>{this.props.stock.currentPrice}</td>
                <td>{this.props.stock.lastUpdate}</td>
            </tr>
        )
    }
}

export default StockList;