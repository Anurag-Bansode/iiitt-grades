package example.example.grading_engine.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")

public class GradeController {

    @GetMapping("/frontend/login/success")
    public String loginSuccess() {
        return "Login Successful";
    }

    @PostMapping()
    public String submitGrades(
            @RequestParam("semester") String semester,
            @RequestParam("courseCode") String courseCode,
            @RequestParam("subjectId") Long subjectId) {

        return String.format("semester=%s, courseCode=%s, subjectId=%d",
                semester, courseCode, subjectId);
    }


    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse res) {
        ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    @GetMapping("/auth/me")
    public Map<String, Object> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return Map.of("authenticated", false);
        }
        // Try to extract userId and role
        Object principal = auth.getPrincipal();
        String userId = principal != null ? principal.toString() : null;
        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElse(null);
        return Map.of(
                "authenticated", true,
                "userId", userId,
                "role", role
        );
    }

}
