import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    device: '/device'
};

function getDevices(callback) {
    let request = new Request(HOST.backend_api + endpoint.device, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });
    console.log('[device-api.getDevices] : ',request.url);
    RestApiClient.performRequest(request, callback);
}

function updateDevice(device, callback) {
    let request = new Request(HOST.backend_api + endpoint.device + "/" + device.name, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        },
        body: JSON.stringify(device)
    });

    console.log('[device-api.updateDevice] : ', request.url);
    RestApiClient.performRequest(request, callback);
}

function getDeviceById(params, callback) {
    let request = new Request(HOST.backend_api + endpoint.device + "/" + params.id, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteDevice(param, callback) {
    let request = new Request(HOST.backend_api + endpoint.device + "/" + param, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });

    console.log('DELETE URL: ', request.url);
    RestApiClient.performRequest(request, callback);
}

function postDevice(device, callback) {
    let request = new Request(HOST.backend_api + endpoint.device, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        },
        body: JSON.stringify(device)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getDevices,
    getDeviceById,
    postDevice,
    deleteDevice,
    updateDevice
};
