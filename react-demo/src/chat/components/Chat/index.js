import React from "react";
import "./Chat.css";

export default function Chat({ msgList, sendMessage }) {

  function onChangeHanlder(){
    var msg = window.msgTextArea.value;
    var status = "w";
    console.log(msg);
    sendMessage({msg : msg,status : status});
  }

  function onSubmitHandler() {
    var msg = window.msgTextArea.value;
    var status = "s";
    sendMessage({msg : msg,status : status});
    window.msgTextArea.value = "";
  }

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
          <span>{chat?.msg}</span>
        </div>
        <div className="chatcard-time">
          <span>{chat?.time}</span>
        </div>
      </div>
    </>
  );
}
