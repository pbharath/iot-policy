package bp.demo.iot.policy.manager.controller;

import bp.demo.iot.policy.manager.exception.DuplicateResourceException;
import bp.demo.iot.policy.manager.model.ContentByAgePolicyRule;
import bp.demo.iot.policy.manager.model.TowerCarrierPlatformPolicyRule;
import bp.demo.iot.policy.manager.service.PolicyService;
import bp.demo.iot.policy.model.exception.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PolicyController {

  private final PolicyService policyService;

  @Autowired
  public PolicyController(PolicyService policyService) {
    this.policyService = policyService;
  }

  private static final String POLICY_RULE_BASE_PATH = "/policy/rule";
  private static final String TOWER_CARRIER_POLICY_RULE_BASE_PATH =
          POLICY_RULE_BASE_PATH + "/towerCarrier";
  private static final String CONTENT_BY_AGE_POLICY_RULE_BASE_PATH =
          POLICY_RULE_BASE_PATH + "/contentByAge";

  @PostMapping(TOWER_CARRIER_POLICY_RULE_BASE_PATH)
  public ResponseEntity createTowerCarrierPlatformPolicyRule(@RequestBody TowerCarrierPlatformPolicyRule tcpPolicyRule)
    throws Exception {

    try {
      TowerCarrierPlatformPolicyRule policyRule = policyService.createNewTowerCarrierPlatformPolicyRule(tcpPolicyRule);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(policyRule.getId())
              .toUri();

      return ResponseEntity.created(uri).body(policyRule);
    }
    catch(DuplicateResourceException dre) {
      ApiError apiError = new ApiError(HttpStatus.CONFLICT,
                                        "DUPLICATE POLICY RULE NOT ALLOWED",
                                        dre.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
      }
  }

  @GetMapping(TOWER_CARRIER_POLICY_RULE_BASE_PATH + "/{id}")
  public ResponseEntity<TowerCarrierPlatformPolicyRule> readTowerCarrierPlatformPolicyRule(@PathVariable String id) {
    Optional<TowerCarrierPlatformPolicyRule> optionalTowerCarrierPlatformPolicyRule =
            policyService.findTowerCarrierPlatformPolicyRule(UUID.fromString(id));

    if(optionalTowerCarrierPlatformPolicyRule.isPresent()) {
      return ResponseEntity.ok(optionalTowerCarrierPlatformPolicyRule.get());
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(TOWER_CARRIER_POLICY_RULE_BASE_PATH)
  public ResponseEntity<List<TowerCarrierPlatformPolicyRule>> readAllTowerCarrierPlatformPolicyRules() {
    List<TowerCarrierPlatformPolicyRule> policyRuleList =
            policyService.findAllTowerCarrierPlatformPolicyRules();

    if(!policyRuleList.isEmpty()) {
      return ResponseEntity.ok(policyRuleList);
    }
    else {
      return ResponseEntity.noContent().build();
    }
  }

  @PostMapping(CONTENT_BY_AGE_POLICY_RULE_BASE_PATH)
  public ResponseEntity createContentByAgePolicyRule(@RequestBody ContentByAgePolicyRule cbaPolicyRule)
    throws Exception {

    try {
      ContentByAgePolicyRule policyRule =
        policyService.createNewContentByAgePolicyRule(cbaPolicyRule);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(policyRule.getId())
              .toUri();

      return ResponseEntity.created(uri).body(policyRule);
    }
    catch (DuplicateResourceException dre) {
      ApiError apiError = new ApiError(HttpStatus.CONFLICT,
              "DUPLICATE POLICY RULE NOT ALLOWED",
              dre.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }

  @GetMapping(CONTENT_BY_AGE_POLICY_RULE_BASE_PATH + "/{id}")
  public ResponseEntity<ContentByAgePolicyRule> readContentByAgePolicyRule(@PathVariable String id) {
    Optional<ContentByAgePolicyRule> optionalTowerCarrierPlatformPolicyRule =
            policyService.findContentByAgePolicyRule(UUID.fromString(id));

    if(optionalTowerCarrierPlatformPolicyRule.isPresent()) {
      return ResponseEntity.ok(optionalTowerCarrierPlatformPolicyRule.get());
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(CONTENT_BY_AGE_POLICY_RULE_BASE_PATH)
  public ResponseEntity<List<ContentByAgePolicyRule>> readAllContentByAgePolicyRules() {
    List<ContentByAgePolicyRule> policyRuleList =
            policyService.findAllContentByAgePolicyRules();

    if(!policyRuleList.isEmpty()) {
      return ResponseEntity.ok(policyRuleList);
    }
    else {
      return ResponseEntity.noContent().build();
    }
  }
}
