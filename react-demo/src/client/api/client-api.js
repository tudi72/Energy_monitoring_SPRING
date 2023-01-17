import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    user: '/client'
};

export function getMyDevices(callback) {
    let request = new Request(HOST.backend_api + endpoint.user, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}


export function getMyDeviceConsumptions(params, callback) {
    let request = new Request(HOST.backend_api + endpoint.user + "/" + params.description + "/" + params.date, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

