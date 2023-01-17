import React from "react";
import Chat from "./../../components/Chat";
import UsersList from "./../../components/UsersList";
import "./ChatPage.css";
import { ChatMessage, ReceiveMsgRequest, Empty } from "./../../protoc/greet_pb";
import { useEffect, useState } from "react";

export default function ChatPage({ client }) {
  const [users, setUsers] = useState([]);
  const [msgList, setMsgList] = useState([]);
  const username = window.localStorage.getItem("username");

  useEffect(() => {
    getAllUsers();
  }, []);

  useEffect(() => {
    const strRq = new ReceiveMsgRequest();
    strRq.setUser(localStorage.getItem("access_token"));

    var chatStream = client.receiveMsg(strRq, {});
    chatStream.on("data", (response) => {
      const from = response.getFrom();
      const msg = response.getMsg();
      const time = response.getTime();
      const to = response.getTo();
      const status= response.getStatus();
      console.log("[ChatPage.receiveMsgRequest] :",{from,username,status});


      if (from === username && status==="s")
      {
        
          // how to identify messages on writing ... should I send the same message back...
          // cannot do it on timestamp, it creates new messages each time a value is inserted
        setMsgList((oldArray) => 
        {
          for(var i = 0; i < oldArray.length; i++){
            console.log(oldArray[i])
            if(oldArray[i].time === time){
              oldArray[i].writing = false;
              return [...oldArray]  
            }
          }
          return [...oldArray] 
        });

      }
      else if(to === username && status==="s")
      {
        // find message -> writing = false
        setMsgList((oldArray) =>[...oldArray, { from, msg, time }]);

      }
      else if(to === username && status==="w"){
        setMsgList((oldArray) =>{
          // PUSH TO ARRAY ONLY ONCE WHEN SUBMITTING
          // message by timestamp => insert if doesnt exist or writing -> true
              for(var i = 0; i < oldArray.length; i++){
                console.log(oldArray[i])
                if(oldArray[i].time === time)
                  return [...oldArray]  
              }
              return [...oldArray, { from, msg, time,writing:true }]
            });
      }

    });


    chatStream.on("status", function (status) {
      console.log(status.code, status.details, status.metadata);
    });

    chatStream.on("end", () => {
      console.log("Stream ended.");
    });
  }, []);

  function getAllUsers() {
    console.log("[ChatPage.getAllUsers]")
    client.getAllUsers(new Empty(), null, (err, response) => {
      let usersList = response?.getUsersList() || [];
      usersList = usersList
        .map((user) => {
          return {
            id: user.array[0],
            name: user.array[1],
          };
        })
        .filter((u) => u.name !== username);
      setUsers(usersList);
    });
  }

  function sendMessage(message) {
    const msg = new ChatMessage();
    msg.setMsg(message.msg);
    msg.setStatus(message.status);
    msg.setTo("admin");
    msg.setFrom(localStorage.getItem("access_token"));
    msg.setTime(new Date().toLocaleString());

    client.sendMsg(msg, null, (err, response) => {
      console.log("[ChatPage.sendMessage]");
    });
  }

  return (
    <div className="chatpage">
      <div className="userslist-section">
        <div
          style={{ paddingBottom: "4px", borderBottom: "1px solid darkgray" }}
        >
          <div>
            <button onClick={getAllUsers}>REFRESH</button>
          </div>
          <div>
            <span>
              Logged in as <b>{username}</b>
            </span>
          </div>
        </div>
        <UsersList users={users} />
      </div>
      <div className="chatpage-section">
        <Chat msgList={msgList} sendMessage={sendMessage} />
      </div>
    </div>
  );
}
