package bp.demo.iot.policy.manager.controller;


import bp.demo.iot.policy.manager.model.TowerCarrierPlatformPolicyRule;
import bp.demo.iot.policy.model.exception.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CONFLICT;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyControllerTest {

  private static final String POLICY_RULE_BASE_PATH = "/policy/rule";
  private static final String TOWER_CARRIER_POLICY_RULE_BASE_PATH =
          POLICY_RULE_BASE_PATH + "/towerCarrier";
  private static final String CONTENT_BY_AGE_POLICY_RULE_BASE_PATH =
          POLICY_RULE_BASE_PATH + "/contentByAge";

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void testPostCreateTowerCarrierPlatformPolicyRule() {

    UUID tcpPolicyRuleId = UUID.randomUUID();
    TowerCarrierPlatformPolicyRule tcpPolicyRule =
            new TowerCarrierPlatformPolicyRule();
    tcpPolicyRule.setId(tcpPolicyRuleId);
    tcpPolicyRule.setCarrierName("AT & T");
    tcpPolicyRule.setTowerName("T1");
    tcpPolicyRule.setPlatformName("iOS");
    tcpPolicyRule.setActive(true);
    tcpPolicyRule.setCreatedBy("U1");
    tcpPolicyRule.setCreatedTimeStamp(new Date());

    ResponseEntity<ApiError> responseEntity =
            restTemplate.postForEntity(TOWER_CARRIER_POLICY_RULE_BASE_PATH,
                    tcpPolicyRule,ApiError.class);

    assertEquals(CONFLICT, responseEntity.getStatusCode());

    assertEquals("DUPLICATE POLICY RULE NOT ALLOWED",
            responseEntity.getBody().getMessage());
  }
}
