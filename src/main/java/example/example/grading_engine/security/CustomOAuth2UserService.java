package example.example.grading_engine.security;

import example.example.grading_engine.enums.academicstructure.AcademicStatus;
import example.example.grading_engine.enums.userauthentication.AuthProvider;
import example.example.grading_engine.enums.userauthentication.UserRole;
import example.example.grading_engine.model.entity.User;
import example.example.grading_engine.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final UserRepository userRepo;

    public CustomOAuth2UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) {
        OAuth2User oauthUser = super.loadUser(req);

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(email);
                    u.setFullName(name);
                    u.setRole(UserRole.STUDENT); // default
                    u.setAuthProvider(AuthProvider.GOOGLE);
                    u.setIsActive(true);
                    u.setAcademicStatus(AcademicStatus.ACTIVE);
                    return userRepo.save(u);
                });

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()
                )),
                oauthUser.getAttributes(),
                "email"
        );
    }
}