## Distributed Web app
Technologies: gRPC remote procedure, JWT token security, RabbitMQ for AMQP protocol, Java Spring, ReactJS UI, Docker Container
The IP addresses for PostreSQL database, UI, backend, RabbitMQ and gRPC were set locally. 
Motivation: App that receives continuous data from remote sensors that simulates Eolian Energy and displays in client UI the Real-time monitorization, data and statistics of Energy consumed per hour/day.
# commands


1. To compile the protoc for java use 

```
protoc -I="./" --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java.exe --grpc-java_out="./../spring-demo/src/main/java" --java_out="./../spring-demo/src/main/java" "./greet.proto"
```

2. To compile the protoc for react use 

```
protoc ./greet.proto --js_out=import_style=commonjs,binary:./../react-demo/src/chat/protoc --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./../react-demo/src/chat/protoc
```

