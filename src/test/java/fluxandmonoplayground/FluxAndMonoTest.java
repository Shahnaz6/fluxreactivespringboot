package fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {
    
    
    @Test
    public void Fluxtest(){
      
       Flux<String> stringFlux=  Flux.just("Spring", "Spring Boot" , "Reactive Spring");
       stringFlux
               .subscribe(System.out::println);
       
    }
}
