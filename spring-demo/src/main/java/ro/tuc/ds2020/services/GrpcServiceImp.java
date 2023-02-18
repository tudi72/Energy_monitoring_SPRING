package ro.tuc.ds2020.services;

import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.grpc.ChatMessage;
import ro.tuc.ds2020.grpc.ChatServiceGrpc;
import ro.tuc.ds2020.grpc.JoinResponse;
import ro.tuc.ds2020.grpc.User;
import ro.tuc.ds2020.grpc.ChatServiceGrpc.ChatServiceImplBase;
import ro.tuc.ds2020.grpc.*;
import ro.tuc.ds2020.security.JWTTokenHelper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class GrpcServiceImp extends ChatServiceGrpc.ChatServiceImplBase{


      private UserService userService;
      private UserList.Builder userList;
      private HashSet<String> userSet;
      private HashMap<ChatMessage,Boolean> shownMessageList = new HashMap<>();
      private Boolean isLoaded = false;

      public GrpcServiceImp(UserService userService){

         userList = UserList.newBuilder();
         userSet = new HashSet<>();

         this.userService = userService;
      }

     @Override
     public void join(User request,StreamObserver<JoinResponse> responseObserver) {

         MyUser user = null; 
         try{
           this.isLoaded = true;
            user = userService.getUser(request.getName());
            String username = user.getName();


            
            if(!userSet.contains(username)){

               User newUser = User.newBuilder()
               .setId(request.getId())
               .setName(username)
               .build();

               userList.addUsers(newUser);
               userSet.add(username);
               log.info("[GrpcService.join] : user " + username + " joins the chat...");
            }
            log.info("[GrpcService.join] : user " + username + " already in chat...");


            JoinResponse response = JoinResponse
            .newBuilder()
            .setError(0)
            .setMsg(user.getName())
            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

         }
        catch(Exception ex){
            log.error("[GrpcService.join] : having error " +  ex.getMessage());
        }



     }

     @Override
     public void sendMsg(ChatMessage request,StreamObserver<Empty> responseObserver) {

            if(!request.getStatus().contains("r")) this.isLoaded = true;
            else this.isLoaded = false;
            if(!request.getFrom().contentEquals("system")){
               try{

                  MyUser user = userService.getUser(request.getFrom());
                  String from = user.getName();
                  String to = request.getTo();  
                  String status = request.getStatus();

                  // log.info("[GrpcServiceImp.sendMsg] " + user.getName()+ " --("+status + ")--> "+ to);
                  // case 1. the admin is sending the message to someone

                  ChatMessage newMessage = ChatMessage.newBuilder()
                  .setFrom(user.getName())
                  .setTo(request.getTo())
                  .setTime(request.getTime())
                  .setMsg(request.getMsg())
                  .setStatus(request.getStatus())
                  .build();

                  if(status.contains("w"))
                     log.info("[GrpcServiceImp.sendMsg] " +  user.getName()+ " --> "+ to + " writing... ");
                  else 
                     log.info("[GrpcServiceImp.sendMsg] " +  user.getName()+ " --> "+ to + " submitted : " + request.getMsg());

                  if(status.contains("s")){
                     shownMessageList.entrySet().stream()
                     .filter(x -> {
                     return (
                        x.getKey().getFrom().contentEquals(from) && 
                        x.getKey().getTo().contentEquals(to)   &&
                        x.getKey().getStatus().contains("w")); 
                     })
                     .findAny()
                     .filter(y -> {
                        log.info("[GrpcServiceImp.DELETE] :" + y.getKey().getFrom() + "\'writing\' to " + y.getKey().getTo());
                        shownMessageList.remove(y.getKey());
                        return true;
                     });

                  }
                  shownMessageList.put(newMessage,true);
                  
               }
               catch(NullPointerException ex){
                  log.error("[GrpcService] : ",ex );
               }
               catch(Exception ex){
                  log.error("[GrpcService] : " + ex.getMessage());
                  log.error("[GrpcService] : " + ex.getLocalizedMessage());
                  log.error("[GrpcService] : " + ex.getCause());
               }
            Empty empty = Empty.newBuilder().build();
         responseObserver.onNext(empty);
         responseObserver.onCompleted();

      }
            else {
               shownMessageList.keySet().stream()
               .filter(x->x.getTime().contentEquals(request.getTime()))
               .findAny()
               .filter(x ->{
                  shownMessageList.put(x,false);
                  return true;
               });
            }
    }

     @Override
     public void receiveMsg(Empty request,StreamObserver<ChatMessage> responseObserver) {

         try{
            // case 1. the admin is receiving messages
            while(true){
               if(isLoaded){
                     shownMessageList.entrySet()
                     .stream()
                     .filter(x -> x.getValue())
                     .forEach(x -> 
                     {
                        shownMessageList.put(x.getKey(),false);
                        responseObserver.onNext(x.getKey());
                     });
                     this.isLoaded = false;
                  }

               }
               
         }
         catch(Exception ex){
            log.error("[GrpcService] : ",ex.getMessage());
         }

      }

     @Override
     public void getAllUsers(Empty request,StreamObserver<UserList> responseObserver) {

         log.info("[GrpcService.getAllUsers] : " + userSet.size() + " users");
         responseObserver.onNext(userList.build());
         responseObserver.onCompleted();
     }
}
