import React from "react";
import "./main-chat-1.css";
import "./main-chat-2.css";

import Header from "./components/Header";
// chat_pb
import { User } from "./protoc/greet_pb";
import { ChatServiceClient } from "./protoc/greet_grpc_web_pb";
import ChatPage from "./pages/ChatPage";
import { useState,useEffect, useRef } from "react";
import {HOST} from "./../commons/hosts";
export const client = new ChatServiceClient(
  HOST.envoy_api,
  null,
  null
);

export default function MainChat() {
  const inputRef = useRef(null);
  const [submitted, setSubmitted] = useState(null);

  useEffect(() => {

    const user = new User();
    user.setId(Date.now());
    user.setName(localStorage.getItem("access_token"));
    console.log("[mainChat.join] : joining...")
    client.join(user, null, (err, response) => {
      if (err) return console.log(err);
      const error = response.getError();
      const msg = response.getMsg();
      const _username =response.getMsg();

      if (error === 1) {
        console.log(error, msg);
        setSubmitted(true);
        window.alert("Username already exists.");
        return;
      }
      window.localStorage.setItem("username", _username.toString());
      user.setName(_username.toString());
      setSubmitted(true);
      // history.push("chatslist");
    });
    
  }, []);

  return (
    <>
      <div>
        <title>ChatApp</title>
        <link rel="icon" href="/favicon.ico" />
      </div>
      <Header />
      <div className="container">
        <main className="main">
          {submitted && <ChatPage client={client} />}
        </main>
      </div>
    </>
  );
}
