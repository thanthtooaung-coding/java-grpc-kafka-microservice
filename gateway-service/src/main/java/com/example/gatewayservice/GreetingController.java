package com.example.gatewayservice;

import com.example.grpc.one.GreetingRequest;
import com.example.grpc.one.GreetingResponse;
import com.example.grpc.two.GreetingServiceTwoGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @GrpcClient("greeting-service-two")
    private GreetingServiceTwoGrpc.GreetingServiceTwoBlockingStub serviceTwoStub;

    @GetMapping("/greet/{name}")
    public String getGreeting(@PathVariable String name) {
        GreetingRequest request = GreetingRequest.newBuilder().setName(name).build();
        GreetingResponse response = serviceTwoStub.sayHello(request);
        return response.getGreeting();
    }
}
