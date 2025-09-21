package com.example.greetingserviceone;

import com.example.grpc.one.GreetingRequest;
import com.example.grpc.one.GreetingResponse;
import com.example.grpc.one.GreetingServiceOneGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceOneImpl extends GreetingServiceOneGrpc.GreetingServiceOneImplBase {

    @Override
    public void sayHi(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String name = request.getName();
        String greeting = "Hi " + name;
        
        GreetingResponse response = GreetingResponse.newBuilder()
                .setGreeting(greeting)
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
