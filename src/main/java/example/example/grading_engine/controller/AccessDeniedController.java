package example.example.grading_engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public Map<String, Object> denied() {
        return Map.of(
                "error", "ACCESS_DENIED",
                "message", "You do not have permission to access this resource"
        );
    }
}