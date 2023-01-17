import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { HOST } from '../hosts';



// TODO : check for a page of erros when devices are overflowing, such as make the table with red or smth 
// TODO : display the values just inserted in energy consumption okay ? 
if(localStorage.getItem('user') == 'client' || localStorage.getItem('user') == 'admin'){

    var socket = new SockJS(HOST.rabbit_api);
    var stompClient = Stomp.over(socket);

    stompClient.connect({login : 'guest', passcode :'guest'},
    function(){
        // successful connection
        console.log('[home]: connected to socket')

        var subscription = stompClient.subscribe('/topic/ds2020', function(payload){
            // message received from subscription
            
            var message = JSON.parse(payload.body);
            console.log('[home] : ',message.measurementValue)
        });
    }
    );
}
               
