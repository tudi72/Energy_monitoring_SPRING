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
  const setTo = "";
  useEffect(() => {
    getAllUsers();
    stream();
  }, []);

  function stream(){
    console.log("enter effect");
    const strRq = new ReceiveMsgRequest();
    strRq.setUser(localStorage.getItem("access_token"));
    
    var chatStream = client.receiveMsg(strRq, {});
    chatStream.on("data", (response) => {
      console.log("[ChatPage.stream] :",response.array);
      
      const from = response.getFrom();
      const msg = response.getMsg();
      const time = response.getTime();
      const to = response.getTo();
      const status= response.getStatus();


      // system --> user
      if(from === "system" && to === username){
        setMsgList((oldArray) => [...oldArray, {from,msg,time,to,status,writing:false}])
        sendMessage({from,msg,time,to,status:"r"})
      }
      //user --> someone (submit)
      else if (from === username && status==="s")
        setMsgList((oldArray) => [...oldArray,{from,msg,time,to,status,writing:false,mine:true}]);
      // someone --> user (submit)
      else if(to === username && status==="s")
        setMsgList((oldArray) =>[...oldArray, {from,msg,time,to,status,writing:false}]);
      // someone --> user (writing)
      else if(to === username && status==="w"){
        setMsgList((oldArray) =>{
              for(var i = 0; i < oldArray.length; i++){
                console.log(oldArray[i])
                if(oldArray[i].time === time)
                  return [...oldArray]  
              }
              return [...oldArray, { from, msg, time,writing:true }]
            });
        }
    })
      

    chatStream.on("status", function (status) {
      console.log(status.code, status.details, status.metadata);
    });

    chatStream.on("end", () => {
      console.log("Stream ended.");
    });
  };

  function getAllUsers() {
    client.getAllUsers(new Empty(), null, (err, response) => {
      let usersList = response?.getUsersList() || [];

      if(usersList === []) console.log("[ChatPage.getAllUsers] : ERROR")
      else  console.log("[ChatPage.getAllUsers]")

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
    msg.setTo(message.to);
    msg.setFrom(message.from);
    msg.setTime(message.date);
    if(message.status==="w")
      console.log("[Chat.onChange] : ",message)
    else if(message.status ==="s")
      console.log("[Chat.onSubmit] : ",message)
    else 
      console.log("no status");
    client.sendMsg(msg, null, (err, response) => {
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
        <Chat msgList={msgList} sendMessage={sendMessage} username={username}/>
      </div>
    </div>
  );
}
