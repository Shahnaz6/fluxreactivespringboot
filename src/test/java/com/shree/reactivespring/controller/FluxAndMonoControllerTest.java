package com.shree.reactivespring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {
    
    //write test case for non blocking end point 
    
    @Autowired
    WebTestClient webTestClient;
    
    @Test
    public void flux_approch1(){
        Flux<Integer> integerFlux =  webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                //invoke the end point 
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        
        //evaluate value from that flux 
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }
    
    @Test
    public void Flux_approch2(){

           webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)   
                .hasSize(4);
                
    }
    
    @Test
    public void flux_approch3(){
        //expected integer list 
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
     EntityExchangeResult<List<Integer>> entityExchangeResult=  webTestClient
             .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
     
     
     assertEquals(expectedIntegerList,entityExchangeResult.getResponseBody());
        
    }

    @Test
    public void flux_approch4(){
        //expected integer list 
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
         webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
         .consumeWith((response) -> {
             assertEquals(expectedIntegerList , response.getResponseBody());
         })
         ;
    }
    
    //infinite stream test case for 2nd controller 
    @Test
    public void fluxStream(){

        Flux<Long> longStreamFlux =  webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                //invoke the end point 
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();
        
        
        StepVerifier.create(longStreamFlux)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .thenCancel()
                .verify();
        
    }
    
    @Test
    public void mono(){
        Integer expectedValue =  new Integer(1);
        webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expectedValue, response.getResponseBody());
                    
                });
                
    }
    
    
}
