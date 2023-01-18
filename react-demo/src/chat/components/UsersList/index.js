import React from "react";
import "./UsersList.css";

export default function UsersList({ users = [] }) {
  return (
    <div className="userslist">
      {users?.map((user, i) => {
        return <UserCard user={user} key={i} onClick={handleUserSelected(user)}/>;
      })}
    </div>
  );
}

function handleUserSelected(user){
  console.log(user?.name);
  window.localStorage.setItem("toUser",user?.name);
}

function UserCard({ user }) {
  return (
    <div className="usercard" id="usercard">
      <div className="usercard-img"></div>
      <div>
        <div className="usercard-name">
          <h3>{user?.name || "No Username"}</h3>
        </div>
      </div>
    </div>
  );
}
