package TaskFlow_api.TaskFlow_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator/health")
public class Health {

    @RequestMapping("/readiness")
    public String healthReadiness() {
        return "OK";
    }

    @RequestMapping("/liveness")
    public String healthLiveness() {
        return "OK";
    }
}
