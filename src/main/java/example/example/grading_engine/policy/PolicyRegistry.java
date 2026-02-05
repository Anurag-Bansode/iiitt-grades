package example.example.grading_engine.policy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PolicyRegistry {

    private final Map<String, GradingPolicy> policies = new HashMap<>();

    public PolicyRegistry(List<GradingPolicy> policyList) {
        for (GradingPolicy p : policyList) {
            policies.put(p.policyVersion(), p);
        }
    }

    public GradingPolicy get(String version) {
        GradingPolicy policy = policies.get(version);
        if (policy == null) {
            throw new IllegalArgumentException("Unknown grading policy: " + version);
        }
        return policy;
    }
}