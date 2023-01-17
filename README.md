# commands


1. To compile the protoc for java use 

```
protoc -I="./" --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java.exe --grpc-java_out="./../spring-grpc-demo/src/main/java" --java_out="./../spring-grpc-demo/src/main/java" "./greet.proto"
```

2. To compile the protoc for react use 

```
protoc ./greet.proto --js_out=import_style=commonjs,binary:./../react-demo/src/chat/protoc --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./../react-demo/src/chat/protoc
```

