package bp.demo.iot.policy.manager.repository;


import bp.demo.iot.policy.manager.repository.dao.TowerCarrierPlatformPolicyRuleDAO;
import bp.demo.iot.policy.manager.repository.pk.TowerCarrierPlatformPolicyRuleKeyDAO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TowerCarrierPlatformPolicyRuleRepositoryTest {

  @MockBean
  TowerCarrierPlatformPolicyRuleRepository repository;

  @Autowired
  ApplicationContext context;

  @BeforeAll
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void givenEmptyRepository_WhenFindByCompositePrimaryKeyInvoked_ReturnsEmptyList() {
    when(repository.findAll()).thenReturn(Collections.EMPTY_LIST);

    TowerCarrierPlatformPolicyRuleRepository tcpPolicyRuleRepositoryFromContext =
            context.getBean(TowerCarrierPlatformPolicyRuleRepository.class);

    Assertions.assertFalse(tcpPolicyRuleRepositoryFromContext
            .findByCompositePrimaryKey("T", "C", "P").isPresent());
  }

  @Test
  public void givenNonEmptyRepository_WhenFindByCompositePrimaryKeyInvoked_ReturnsMatchingPolicyRule() {

    String towerName = "T";
    String carrierName = "C";
    String platformName = "P";

    BDDMockito.given(repository.findByCompositePrimaryKey(towerName,
            carrierName, platformName)).willAnswer(a -> {
              Object[] args = a.getArguments();

              String towerNameArg = (String) args[0];
              String carrierNameArg = (String) args[1];
              String platformNameArg = (String) args[2];

              if (towerNameArg.equalsIgnoreCase(towerName) && carrierNameArg.equalsIgnoreCase(carrierName) && platformNameArg.equalsIgnoreCase(platformName)) {
                return Optional.of(new TowerCarrierPlatformPolicyRuleDAO());
              } else {
                return Optional.empty();
              }
            });

    TowerCarrierPlatformPolicyRuleRepository tcpPolicyRuleRepositoryFromContext =
            context.getBean(TowerCarrierPlatformPolicyRuleRepository.class);

    Assertions.assertTrue(tcpPolicyRuleRepositoryFromContext
            .findByCompositePrimaryKey(towerName, carrierName, platformName).isPresent());
    Assertions.assertFalse(tcpPolicyRuleRepositoryFromContext
            .findByCompositePrimaryKey("A", "B", "C").isPresent());
  }

  @Test
  public void givenEmptyRepository_WhenRetrieveAllPolicyRulesInvoked_ReturnsEmptyResult() {

    BDDMockito.given(repository.findAll()).willReturn(Collections.emptyList());

    List<TowerCarrierPlatformPolicyRuleDAO> policyRuleDAOList =
            repository.findAll();

    Assertions.assertTrue(policyRuleDAOList.isEmpty());
  }

  @Test
  public void givenNewPolicyRules_WhenCreateNewPolicyRuleInvoked_PersistsIfNonExistent() {

    String carrierName = "C";
    String towerName = "T";
    String platformName = "P";

    TowerCarrierPlatformPolicyRuleDAO nonExistingPolicyRuleDAO  =
            createTowerCarrierPlatformPolicyRuleDAO(
                    carrierName + "1",
                    towerName  + "1",
                    platformName + "1");

    TowerCarrierPlatformPolicyRuleDAO existingPolicyRuleDAO  =
            createTowerCarrierPlatformPolicyRuleDAO(carrierName, towerName, platformName);

    BDDMockito.given(repository.findByCompositePrimaryKey(carrierName,
            towerName, platformName)).willAnswer(a -> {
      Object[] args = a.getArguments();

      String carrierNameArg = (String) args[0];
      String towerNameArg = (String) args[1];
      String platformNameArg = (String) args[2];

      if (carrierNameArg.equalsIgnoreCase(carrierName) &&
              towerNameArg.equalsIgnoreCase(towerName) &&
              platformNameArg.equalsIgnoreCase(platformName)) {
        return null;
      } else {
        return nonExistingPolicyRuleDAO;
      }
    });


    when(repository.insert(any(TowerCarrierPlatformPolicyRuleDAO.class)))
            .thenReturn(any(TowerCarrierPlatformPolicyRuleDAO.class));

    TowerCarrierPlatformPolicyRuleDAO existingDAO =
            repository.insert(existingPolicyRuleDAO);
    Assertions.assertNull(existingDAO);

    TowerCarrierPlatformPolicyRuleDAO nonExistingDAO =
            repository.insert(nonExistingPolicyRuleDAO);
    Assertions.assertNotNull(nonExistingPolicyRuleDAO);
  }

  private TowerCarrierPlatformPolicyRuleDAO createTowerCarrierPlatformPolicyRuleDAO(String carrierName, String towerName, String platformName){
    TowerCarrierPlatformPolicyRuleKeyDAO keyDAO =
            new TowerCarrierPlatformPolicyRuleKeyDAO(carrierName, towerName, platformName);
    TowerCarrierPlatformPolicyRuleDAO policyRuleDAO =
            new TowerCarrierPlatformPolicyRuleDAO();
    policyRuleDAO.setKeyDAO(keyDAO);
    policyRuleDAO.setId(UUID.randomUUID());
    policyRuleDAO.setActive(true);
    policyRuleDAO.setCreatedTimeStamp(Date.from(OffsetDateTime.now(ZoneOffset.UTC).toInstant()));
    policyRuleDAO.setCreatedBy("U1");

    return policyRuleDAO;
  }

}
