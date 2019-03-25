package bp.demo.iot.policy.manager.controller;

import bp.demo.iot.policy.manager.service.PolicyService;
import bp.demo.iot.policy.manager.model.ContentByAgePolicyRule;
import bp.demo.iot.policy.manager.model.TowerCarrierPlatformPolicyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PolicyController {

  @Autowired
  private PolicyService policyService;

  private static final String POLICY_RULE_BASE_PATH = "/policy/rule";

  @PostMapping(POLICY_RULE_BASE_PATH + "/towerCarrier")
  TowerCarrierPlatformPolicyRule createTowerCarrierPlatformPolicyRule(@RequestBody TowerCarrierPlatformPolicyRule tcppr)
    throws Exception {

    return policyService.createNewTowerCarrierPlatformPolicyRule(tcppr);

  }

  @PostMapping(POLICY_RULE_BASE_PATH + "/contentByAge")
  ContentByAgePolicyRule createContentByAgePolicyRule(@RequestBody ContentByAgePolicyRule cbapr)
    throws Exception {

    return policyService.createNewContentByAgePolicyRule(cbapr);
  }
}
