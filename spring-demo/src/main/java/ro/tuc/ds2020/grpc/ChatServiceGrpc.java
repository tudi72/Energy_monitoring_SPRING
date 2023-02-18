package ro.tuc.ds2020.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: greet.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final String SERVICE_NAME = "ro.tuc.ds2020.grpc.ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.User,
      ro.tuc.ds2020.grpc.JoinResponse> getJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "join",
      requestType = ro.tuc.ds2020.grpc.User.class,
      responseType = ro.tuc.ds2020.grpc.JoinResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.User,
      ro.tuc.ds2020.grpc.JoinResponse> getJoinMethod() {
    io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.User, ro.tuc.ds2020.grpc.JoinResponse> getJoinMethod;
    if ((getJoinMethod = ChatServiceGrpc.getJoinMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getJoinMethod = ChatServiceGrpc.getJoinMethod) == null) {
          ChatServiceGrpc.getJoinMethod = getJoinMethod =
              io.grpc.MethodDescriptor.<ro.tuc.ds2020.grpc.User, ro.tuc.ds2020.grpc.JoinResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "join"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.User.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.JoinResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("join"))
              .build();
        }
      }
    }
    return getJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.ChatMessage,
      ro.tuc.ds2020.grpc.Empty> getSendMsgMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendMsg",
      requestType = ro.tuc.ds2020.grpc.ChatMessage.class,
      responseType = ro.tuc.ds2020.grpc.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.ChatMessage,
      ro.tuc.ds2020.grpc.Empty> getSendMsgMethod() {
    io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.ChatMessage, ro.tuc.ds2020.grpc.Empty> getSendMsgMethod;
    if ((getSendMsgMethod = ChatServiceGrpc.getSendMsgMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getSendMsgMethod = ChatServiceGrpc.getSendMsgMethod) == null) {
          ChatServiceGrpc.getSendMsgMethod = getSendMsgMethod =
              io.grpc.MethodDescriptor.<ro.tuc.ds2020.grpc.ChatMessage, ro.tuc.ds2020.grpc.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendMsg"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.ChatMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("sendMsg"))
              .build();
        }
      }
    }
    return getSendMsgMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty,
      ro.tuc.ds2020.grpc.ChatMessage> getReceiveMsgMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "receiveMsg",
      requestType = ro.tuc.ds2020.grpc.Empty.class,
      responseType = ro.tuc.ds2020.grpc.ChatMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty,
      ro.tuc.ds2020.grpc.ChatMessage> getReceiveMsgMethod() {
    io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty, ro.tuc.ds2020.grpc.ChatMessage> getReceiveMsgMethod;
    if ((getReceiveMsgMethod = ChatServiceGrpc.getReceiveMsgMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getReceiveMsgMethod = ChatServiceGrpc.getReceiveMsgMethod) == null) {
          ChatServiceGrpc.getReceiveMsgMethod = getReceiveMsgMethod =
              io.grpc.MethodDescriptor.<ro.tuc.ds2020.grpc.Empty, ro.tuc.ds2020.grpc.ChatMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "receiveMsg"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.ChatMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("receiveMsg"))
              .build();
        }
      }
    }
    return getReceiveMsgMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty,
      ro.tuc.ds2020.grpc.UserList> getGetAllUsersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllUsers",
      requestType = ro.tuc.ds2020.grpc.Empty.class,
      responseType = ro.tuc.ds2020.grpc.UserList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty,
      ro.tuc.ds2020.grpc.UserList> getGetAllUsersMethod() {
    io.grpc.MethodDescriptor<ro.tuc.ds2020.grpc.Empty, ro.tuc.ds2020.grpc.UserList> getGetAllUsersMethod;
    if ((getGetAllUsersMethod = ChatServiceGrpc.getGetAllUsersMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getGetAllUsersMethod = ChatServiceGrpc.getGetAllUsersMethod) == null) {
          ChatServiceGrpc.getGetAllUsersMethod = getGetAllUsersMethod =
              io.grpc.MethodDescriptor.<ro.tuc.ds2020.grpc.Empty, ro.tuc.ds2020.grpc.UserList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllUsers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ro.tuc.ds2020.grpc.UserList.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("getAllUsers"))
              .build();
        }
      }
    }
    return getGetAllUsersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChatServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void join(ro.tuc.ds2020.grpc.User request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.JoinResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinMethod(), responseObserver);
    }

    /**
     */
    public void sendMsg(ro.tuc.ds2020.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMsgMethod(), responseObserver);
    }

    /**
     */
    public void receiveMsg(ro.tuc.ds2020.grpc.Empty request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.ChatMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReceiveMsgMethod(), responseObserver);
    }

    /**
     */
    public void getAllUsers(ro.tuc.ds2020.grpc.Empty request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.UserList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllUsersMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getJoinMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ro.tuc.ds2020.grpc.User,
                ro.tuc.ds2020.grpc.JoinResponse>(
                  this, METHODID_JOIN)))
          .addMethod(
            getSendMsgMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ro.tuc.ds2020.grpc.ChatMessage,
                ro.tuc.ds2020.grpc.Empty>(
                  this, METHODID_SEND_MSG)))
          .addMethod(
            getReceiveMsgMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                ro.tuc.ds2020.grpc.Empty,
                ro.tuc.ds2020.grpc.ChatMessage>(
                  this, METHODID_RECEIVE_MSG)))
          .addMethod(
            getGetAllUsersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ro.tuc.ds2020.grpc.Empty,
                ro.tuc.ds2020.grpc.UserList>(
                  this, METHODID_GET_ALL_USERS)))
          .build();
    }
  }

  /**
   */
  public static final class ChatServiceStub extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     */
    public void join(ro.tuc.ds2020.grpc.User request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.JoinResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendMsg(ro.tuc.ds2020.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMsgMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receiveMsg(ro.tuc.ds2020.grpc.Empty request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.ChatMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getReceiveMsgMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllUsers(ro.tuc.ds2020.grpc.Empty request,
        io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.UserList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllUsersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ro.tuc.ds2020.grpc.JoinResponse join(ro.tuc.ds2020.grpc.User request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinMethod(), getCallOptions(), request);
    }

    /**
     */
    public ro.tuc.ds2020.grpc.Empty sendMsg(ro.tuc.ds2020.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMsgMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<ro.tuc.ds2020.grpc.ChatMessage> receiveMsg(
        ro.tuc.ds2020.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getReceiveMsgMethod(), getCallOptions(), request);
    }

    /**
     */
    public ro.tuc.ds2020.grpc.UserList getAllUsers(ro.tuc.ds2020.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllUsersMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ro.tuc.ds2020.grpc.JoinResponse> join(
        ro.tuc.ds2020.grpc.User request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ro.tuc.ds2020.grpc.Empty> sendMsg(
        ro.tuc.ds2020.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMsgMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ro.tuc.ds2020.grpc.UserList> getAllUsers(
        ro.tuc.ds2020.grpc.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllUsersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_JOIN = 0;
  private static final int METHODID_SEND_MSG = 1;
  private static final int METHODID_RECEIVE_MSG = 2;
  private static final int METHODID_GET_ALL_USERS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_JOIN:
          serviceImpl.join((ro.tuc.ds2020.grpc.User) request,
              (io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.JoinResponse>) responseObserver);
          break;
        case METHODID_SEND_MSG:
          serviceImpl.sendMsg((ro.tuc.ds2020.grpc.ChatMessage) request,
              (io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.Empty>) responseObserver);
          break;
        case METHODID_RECEIVE_MSG:
          serviceImpl.receiveMsg((ro.tuc.ds2020.grpc.Empty) request,
              (io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.ChatMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_USERS:
          serviceImpl.getAllUsers((ro.tuc.ds2020.grpc.Empty) request,
              (io.grpc.stub.StreamObserver<ro.tuc.ds2020.grpc.UserList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ro.tuc.ds2020.grpc.Greet.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getJoinMethod())
              .addMethod(getSendMsgMethod())
              .addMethod(getReceiveMsgMethod())
              .addMethod(getGetAllUsersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
