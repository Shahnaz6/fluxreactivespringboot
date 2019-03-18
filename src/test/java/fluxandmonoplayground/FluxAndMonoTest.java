package fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {
    
    
    @Test
    public void Fluxtest(){
      
       Flux<String> stringFlux=  Flux.just("Spring", "Spring Boot" , "Reactive Spring")

/*
               .concatWith(Flux.error(new RuntimeException("Exception Occured")))
*/
               .concatWith(Flux.just("After Error"))

               .log();
       stringFlux
               .subscribe(System.out::println,
                       (e) -> System.err.println("Exception is"+e)
               ,() ->System.out.println("Completed"));
       
    }
    
    //Testing Flux 
    
}
