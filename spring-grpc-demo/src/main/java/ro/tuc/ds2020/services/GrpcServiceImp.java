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
         ChatMessage firstMessage = ChatMessage.newBuilder()
            .setFrom("system")
            .setTime(String.valueOf(Date.from(Instant.now())))
            .setMsg("System welcomes you")
            .build();

         shownMessageList.put(firstMessage,true);
         this.userService = userService;
      }

     @Override
     public void join(User request,StreamObserver<JoinResponse> responseObserver) {

         MyUser user = null; 
         try{
           this.isLoaded = true;
            user = userService.getUser(request.getName());
            String username = user.getName();

            User newUser = User.newBuilder()
               .setId(request.getId())
               .setName(username)
               .build();
            
            if(!userSet.contains(username)){
               userList.addUsers(newUser);
            }

            log.info("[GrpcService.join] : user " + username + " joins the chat...");

        }
        catch(Exception ex){
            log.error("[GrpcService.join] : having error " +  ex.getMessage());
        }

         JoinResponse response = JoinResponse
            .newBuilder()
            .setError(0)
            .setMsg("Welcome " + user.getName() + " to the private chat...")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();


     }

     @Override
     public void sendMsg(ChatMessage request,StreamObserver<Empty> responseObserver) {
        // find user and redirect him to ...
         try{
            this.isLoaded = true;
            JWTTokenHelper helper = new JWTTokenHelper();
            String role = helper.getAllRolesFromToken(request.getFrom());
            String name = helper.getUsernameFromToken(request.getFrom());
            // case 1. the admin is sending the message to someone

            ChatMessage newMessage = ChatMessage.newBuilder()
            .setFrom(name)
            .setTo(request.getTo())
            .setTime(request.getTime())
            .setMsg(request.getMsg())
            .setStatus(request.getStatus())
            .build();

            log.info("[GrpcServiceImp.sendMsg] : ROLE " + role + " and name " + name);
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

     @Override
     public void receiveMsg(Empty request,StreamObserver<ChatMessage> responseObserver) {

         try{
            Boolean isAdmin = false;
            // case 1. the admin is receiving messages
            
            while(true){

               if(isLoaded){
                     log.info("[GrpcService] : user receives message...");
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

         log.info("[GrpcService.getAllUsers]");
         responseObserver.onNext(userList.build());
         responseObserver.onCompleted();
     }
}
