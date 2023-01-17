import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { HOST } from '../hosts';

export default function connect(arg) {

	// create socket 
	var socket = new SockJS(HOST.rabbit_api);
	var stompClient = Stomp.over(socket);

	// connect socket
	stompClient.connect({login: 'guest',passcode:'guest'}, 
	
	function (){
	
			// successful connection
			console.log('[connection-handler]: connected to socket')

			
		var subscription = stompClient.subscribe('/topic/ds2020', 
			function(payload){
			// message received from subscription

			var message = JSON.parse(payload.body);
			console.log('[home] : ',message.measurementValue)
		});

			// send device ID
			stompClient.send("/app/chat.newDevice", {}, JSON.stringify({
				timestamp : (new Date()).getTime() ,
				deviceID : arg,
				measurementValue : 0.0
		}))

	}
);
	

}



