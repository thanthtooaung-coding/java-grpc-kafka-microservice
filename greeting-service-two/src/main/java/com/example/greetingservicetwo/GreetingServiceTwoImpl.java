package com.example.greetingservicetwo;

import com.example.grpc.one.GreetingRequest;
import com.example.grpc.one.GreetingResponse;
import com.example.grpc.one.GreetingServiceOneGrpc;
import com.example.grpc.two.GreetingServiceTwoGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@GrpcService
public class GreetingServiceTwoImpl extends GreetingServiceTwoGrpc.GreetingServiceTwoImplBase {

    @GrpcClient("greeting-service-one")
    private GreetingServiceOneGrpc.GreetingServiceOneBlockingStub serviceOneStub;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private static final String KAFKA_TOPIC = "greetings-topic";

    @Override
    public void sayHello(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        // Call Service One
        GreetingResponse responseFromOne = serviceOneStub.sayHi(request);

        // Prepare response for Service Two
        String finalGreeting = "Hello " + request.getName() + ". " + responseFromOne.getGreeting();
        
        GreetingResponse finalResponse = GreetingResponse.newBuilder()
                .setGreeting(finalGreeting)
                .build();
                
        // Publish to Kafka
        kafkaTemplate.send(KAFKA_TOPIC, finalGreeting);

        responseObserver.onNext(finalResponse);
        responseObserver.onCompleted();
    }
}