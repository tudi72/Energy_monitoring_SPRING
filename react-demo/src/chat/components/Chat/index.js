import React from "react";
import "./Chat.css";
import Logo from "./../../../commons/images/typing.gif";

export default function Chat({ msgList, sendMessage,username}) {
  function onChangeHanlder(){
    var msg = window.msgTextArea.value;
    var from = username;
    var to = window.localStorage.getItem("toUser")
    var date = new Date().toLocaleString()
    if(from !== to)
      sendMessage({msg : msg,from : from,to: to,status : "w"});
  }



  function onSubmitHandler() {
    var msg = window.msgTextArea.value;
    var date = new Date().toLocaleString()
    var to = window.localStorage.getItem("toUser")
    var from = username;
    if(from !== to)
      sendMessage({msg : msg,from : from,to: to,status : "s",date: date});
    window.msgTextArea.value = "";
  }

  console.log("USERNAME",username);
  return (
    <div className="chat">
      <div className="chat-header">
        <h3>Group Messages</h3>
      </div>
      <div className="chat-list">
        {msgList?.map((chat, i) => (
          <ChatCard chat={chat} key={i} />
        ))}
      </div>
      <div className="chat-input">
        <div style={{ flex: "3 1 90%" }}>
          <textarea id="msgTextArea" onChange={onChangeHanlder}/>
        </div>
        <div
          style={{
            paddingLeft: "5px",
            display: "flex",
            alignItems: "center",
            justifyContent: "flex-end",
          }}
        >
          <button onClick={onSubmitHandler}>Send</button>
        </div>
      </div>
    </div>
  );
}

function ChatCard({ chat }) {
  return (
    <>
      <div style={{ fontSize: "9px", marginLeft: "4px", paddingLeft: "8px" }}>
        <span>{chat?.from}</span>
      </div>
      <div
        className={
          chat?.mine ?
          chat?.writing ? "chatcard chatcard-mine-writing":"chatcard chatcard-mine"
          : 
          chat?.writing ? "chatcard chatcard-friend-writing":"chatcard chatcard-friend"
        }
      >
        <div className="chatcard-msg">
          {chat?.writing && <img src={Logo} alt="loading..."/>}
          <span>{chat?.msg}</span>
        </div>
        <div className="chatcard-time">
          <span>{chat?.time}</span>
        </div>
      </div>
    </>
  );
}
