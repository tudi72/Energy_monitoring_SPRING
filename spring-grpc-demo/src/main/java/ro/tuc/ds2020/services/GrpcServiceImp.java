package ro.tuc.ds2020.services;

import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.grpc.ChatMessage;
import ro.tuc.ds2020.grpc.ChatServiceGrpc;
import ro.tuc.ds2020.grpc.JoinResponse;
import ro.tuc.ds2020.grpc.User;
import ro.tuc.ds2020.grpc.*;
import ro.tuc.ds2020.repositories.MyUserRepository;
import ro.tuc.ds2020.repositories.RoleRepository;
import ro.tuc.ds2020.security.JWTTokenHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;

import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class GrpcServiceImp extends ChatServiceGrpc.ChatServiceImplBase{
      
      private UserService userService;
      private UserList.Builder userList;
      private HashSet<String> userSet;
      private ArrayList<ChatMessage> fromAdmin = new ArrayList<>();
      private ArrayList<ChatMessage> toAdmin = new ArrayList<>();

      public GrpcServiceImp(UserService userService){

         userList = UserList.newBuilder();
         userSet = new HashSet<>();
         ChatMessage firstMessage = ChatMessage.newBuilder()
            .setFrom("system")
            .setTime(String.valueOf(Date.from(Instant.now())))
            .setMsg("System welcomes you")
            .build();

            fromAdmin.add(firstMessage);
            toAdmin.add(firstMessage);

         this.userService = userService;
      }

     @Override
     public void join(User request,StreamObserver<JoinResponse> responseObserver) {

         MyUser user = null; 
         try{
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
            JWTTokenHelper helper = new JWTTokenHelper();
            String role = helper.getAllRolesFromToken(request.getFrom());
            String name = helper.getUsernameFromToken(request.getFrom());

            // case 1. the admin is sending the message to someone

            ChatMessage newMessage = ChatMessage.newBuilder()
            .setFrom(name)
            .setTime(request.getTime())
            .setMsg(request.getMsg())
            .build();

            if(role.equals("ROLE_ADMIN")){
               log.info("[GrpcServiceImp.sendMsg]: admin sends \"" + request.getMsg() +" \"");

               fromAdmin.add(newMessage);
            }
            else{
               // case 2. the client is sending the message to admin
               log.info("[GrpcServiceImp.sendMsg]: client sends \"" + request.getMsg() +" \"");
               toAdmin.add(newMessage);
            }
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
            Boolean isAdmin = true;

            // case 1. the admin is receiving messages
            if(isAdmin){
               log.info("[GrpcService] : admin receives message...");
               
               toAdmin
                  .stream()
                  .forEach(responseObserver::onNext);

            }
            else{
               // case 2. the client is receiving messages from admin
               log.info("[GrpcService] : client receives message...");
               fromAdmin
                  .stream()
                  .forEach(responseObserver::onNext);

            }
               
         }
         catch(Exception ex){
            log.error("[GrpcService] : ",ex.getMessage());
            responseObserver.onCompleted();
         }

      }

     @Override
     public void getAllUsers(Empty request,StreamObserver<UserList> responseObserver) {

      log.info("[GrpcService.getAllUsers]");

         responseObserver.onNext(userList.build());
         responseObserver.onCompleted();
     }
}
