import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    user: '/user'
};

export function mapDeviceToUser(obj, callback) {
    let request = new Request(HOST.backend_api + "/admin/" + obj.name + "_" + obj.device, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}


function getUsers(callback) {
    let request = new Request(HOST.backend_api + endpoint.user, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function updateUser(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.user + "/" + user.name, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        },
        body: JSON.stringify(user)
    });

    console.log('Modify user : ', request.url);
    RestApiClient.performRequest(request, callback);
}

function getUserById(params, callback) {
    let request = new Request(HOST.backend_api + endpoint.user + "/" + params.id, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteUser(param, callback) {
    let request = new Request(HOST.backend_api + endpoint.user + "/" + param, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
    });

    console.log('DELETE URL: ', request.url);
    RestApiClient.performRequest(request, callback);
}

function postUser(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.user, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


export {
    getUsers,
    getUserById,
    postUser,
    deleteUser,
    updateUser
};
