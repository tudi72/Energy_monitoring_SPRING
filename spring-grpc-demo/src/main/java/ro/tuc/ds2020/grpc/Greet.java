// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: greet.proto

package ro.tuc.ds2020.grpc;

public final class Greet {
  private Greet() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_ChatMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_ChatMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_Empty_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_Empty_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_UserList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_UserList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_JoinResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_JoinResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ro_tuc_ds2020_grpc_ReceiveMsgRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ro_tuc_ds2020_grpc_ReceiveMsgRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013greet.proto\022\022ro.tuc.ds2020.grpc\"6\n\013Cha" +
      "tMessage\022\014\n\004from\030\001 \001(\t\022\013\n\003msg\030\002 \001(\t\022\014\n\004t" +
      "ime\030\003 \001(\t\" \n\004User\022\n\n\002id\030\001 \001(\t\022\014\n\004name\030\002 " +
      "\001(\t\"\007\n\005Empty\"3\n\010UserList\022\'\n\005users\030\001 \003(\0132" +
      "\030.ro.tuc.ds2020.grpc.User\"*\n\014JoinRespons" +
      "e\022\r\n\005error\030\001 \001(\005\022\013\n\003msg\030\002 \001(\t\"!\n\021Receive" +
      "MsgRequest\022\014\n\004user\030\001 \001(\t2\264\002\n\013ChatService" +
      "\022D\n\004join\022\030.ro.tuc.ds2020.grpc.User\032 .ro." +
      "tuc.ds2020.grpc.JoinResponse\"\000\022G\n\007sendMs" +
      "g\022\037.ro.tuc.ds2020.grpc.ChatMessage\032\031.ro." +
      "tuc.ds2020.grpc.Empty\"\000\022L\n\nreceiveMsg\022\031." +
      "ro.tuc.ds2020.grpc.Empty\032\037.ro.tuc.ds2020" +
      ".grpc.ChatMessage\"\0000\001\022H\n\013getAllUsers\022\031.r" +
      "o.tuc.ds2020.grpc.Empty\032\034.ro.tuc.ds2020." +
      "grpc.UserList\"\000B\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ro_tuc_ds2020_grpc_ChatMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ro_tuc_ds2020_grpc_ChatMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_ChatMessage_descriptor,
        new java.lang.String[] { "From", "Msg", "Time", });
    internal_static_ro_tuc_ds2020_grpc_User_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ro_tuc_ds2020_grpc_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_User_descriptor,
        new java.lang.String[] { "Id", "Name", });
    internal_static_ro_tuc_ds2020_grpc_Empty_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ro_tuc_ds2020_grpc_Empty_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_Empty_descriptor,
        new java.lang.String[] { });
    internal_static_ro_tuc_ds2020_grpc_UserList_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ro_tuc_ds2020_grpc_UserList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_UserList_descriptor,
        new java.lang.String[] { "Users", });
    internal_static_ro_tuc_ds2020_grpc_JoinResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_ro_tuc_ds2020_grpc_JoinResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_JoinResponse_descriptor,
        new java.lang.String[] { "Error", "Msg", });
    internal_static_ro_tuc_ds2020_grpc_ReceiveMsgRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_ro_tuc_ds2020_grpc_ReceiveMsgRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ro_tuc_ds2020_grpc_ReceiveMsgRequest_descriptor,
        new java.lang.String[] { "User", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
