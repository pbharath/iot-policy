package bp.demo.iot.policy.manager.service;

import bp.demo.iot.policy.kafka.model.ContentByAgeData;
import bp.demo.iot.policy.kafka.model.TowerCarrierPlatformData;
import bp.demo.iot.policy.kafka.publisher.ContentByAgeDataMessagePublisher;
import bp.demo.iot.policy.kafka.publisher.TowerCarrierPlatformDataMessagePublisher;
import bp.demo.iot.policy.manager.exception.DuplicateResourceException;
import bp.demo.iot.policy.manager.model.ContentByAgePolicyRule;
import bp.demo.iot.policy.manager.model.TowerCarrierPlatformPolicyRule;
import bp.demo.iot.policy.manager.repository.ContentByAgePolicyRuleRepository;
import bp.demo.iot.policy.manager.repository.TowerCarrierPlatformPolicyRuleRepository;
import bp.demo.iot.policy.manager.repository.dao.ContentByAgePolicyRuleDAO;
import bp.demo.iot.policy.manager.repository.dao.TowerCarrierPlatformPolicyRuleDAO;
import bp.demo.iot.policy.manager.service.helper.PolicyServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PolicyService
  implements Serializable {

  private final TowerCarrierPlatformPolicyRuleRepository towerCarrierPlatformPolicyRuleRepository;

  private final ContentByAgePolicyRuleRepository contentByAgePolicyRuleRepository;

  private final TowerCarrierPlatformDataMessagePublisher towerCarrierPlatformDataMessagePublisher;

  private final ContentByAgeDataMessagePublisher contentByAgeDataMessagePublisher;

  private final PolicyServiceHelper policyServiceHelper;

  @Autowired
  public PolicyService(TowerCarrierPlatformPolicyRuleRepository towerCarrierPlatformPolicyRuleRepository,
                       ContentByAgePolicyRuleRepository contentByAgePolicyRuleRepository,
                       TowerCarrierPlatformDataMessagePublisher towerCarrierPlatformDataMessagePublisher,
                       ContentByAgeDataMessagePublisher contentByAgeDataMessagePublisher,
                       PolicyServiceHelper policyServiceHelper) {

    this.towerCarrierPlatformPolicyRuleRepository =
            towerCarrierPlatformPolicyRuleRepository;
    this.contentByAgePolicyRuleRepository = contentByAgePolicyRuleRepository;
    this.towerCarrierPlatformDataMessagePublisher =
            towerCarrierPlatformDataMessagePublisher;
    this.contentByAgeDataMessagePublisher = contentByAgeDataMessagePublisher;
    this.policyServiceHelper = policyServiceHelper;

  }

  public TowerCarrierPlatformPolicyRule

    createNewTowerCarrierPlatformPolicyRule(TowerCarrierPlatformPolicyRule tcpPolicyRule)
          throws Exception {

    Optional<TowerCarrierPlatformPolicyRuleDAO> policyRuleDAO =
            towerCarrierPlatformPolicyRuleRepository
                    .findByCompositePrimaryKey(
                            tcpPolicyRule.getTowerName(),
                            tcpPolicyRule.getCarrierName(),
                            tcpPolicyRule.getPlatformName());

    if(!policyRuleDAO.isPresent()) {

      TowerCarrierPlatformPolicyRuleDAO oldDAO =
              policyServiceHelper.convertToDAO(tcpPolicyRule);
      oldDAO.setCreatedTimeStamp(new Date());
      oldDAO.setId(UUID.randomUUID());

      TowerCarrierPlatformPolicyRuleDAO newDAO =
              towerCarrierPlatformPolicyRuleRepository.insert(oldDAO);

      TowerCarrierPlatformData tcpData = new TowerCarrierPlatformData(
              newDAO.getKeyDAO().getTowerName(),
              newDAO.getKeyDAO().getCarrierName(),
              newDAO.getKeyDAO().getPlatformName(),
              newDAO.getActive()
      );

      Message<TowerCarrierPlatformData> message =
              MessageBuilder.withPayload(tcpData)
              .setHeader(KafkaHeaders.MESSAGE_KEY, newDAO.getKeyDAO().getTowerName())
              .setHeader(KafkaHeaders.TOPIC, towerCarrierPlatformDataMessagePublisher.getTopic())
              .build();
      towerCarrierPlatformDataMessagePublisher.sendMessage(message);

      return policyServiceHelper.convertToEntity(newDAO);
    }
    else
      throw new DuplicateResourceException("Rule exists for {" + tcpPolicyRule.getTowerName() +", " + tcpPolicyRule.getCarrierName() + ", " + tcpPolicyRule.getPlatformName() +"}");
  }

  public Optional<TowerCarrierPlatformPolicyRule> findTowerCarrierPlatformPolicyRule(UUID id) {
    Optional<TowerCarrierPlatformPolicyRuleDAO> optionalPolicyRuleDAO =
              towerCarrierPlatformPolicyRuleRepository.findById(id);

    if(optionalPolicyRuleDAO.isPresent()) {
      TowerCarrierPlatformPolicyRule policyRule =
              policyServiceHelper.convertToEntity(optionalPolicyRuleDAO.get());

      return Optional.of(policyRule);
    }
    else {
      return Optional.empty();
    }

  }

  public List<TowerCarrierPlatformPolicyRule> findAllTowerCarrierPlatformPolicyRules() {

    List<TowerCarrierPlatformPolicyRuleDAO> daoList =
      towerCarrierPlatformPolicyRuleRepository.findAll();

    return daoList.stream().map( policyServiceHelper::convertToEntity).collect(Collectors.toList());

  }

  public ContentByAgePolicyRule createNewContentByAgePolicyRule(ContentByAgePolicyRule cbaPolicyRule)
    throws Exception {

    List<ContentByAgePolicyRuleDAO> policyRuleDAOList =
      contentByAgePolicyRuleRepository.findByAgeRange(
              cbaPolicyRule.getStartAge(), cbaPolicyRule.getEndAge());

    if(policyRuleDAOList.size() == 0) {

      ContentByAgePolicyRuleDAO oldDAO =
              policyServiceHelper.convertToDAO(cbaPolicyRule);
      oldDAO.setId(UUID.randomUUID());
      oldDAO.setCreatedTimeStamp(new Date());

      ContentByAgePolicyRuleDAO persistedDAO =
              contentByAgePolicyRuleRepository.insert(oldDAO);

      ContentByAgeData cyaData = new ContentByAgeData(
              persistedDAO.getId(),
              persistedDAO.getStartAge(),
              persistedDAO.getEndAge(),
              persistedDAO.getContentSet()
      );

      Message<ContentByAgeData> message =
              MessageBuilder.withPayload(cyaData)
                      .setHeader(KafkaHeaders.MESSAGE_KEY,
                              persistedDAO.getId().toString())
                      .setHeader(KafkaHeaders.TOPIC,
                              contentByAgeDataMessagePublisher.getTopic())
                      .build();
      contentByAgeDataMessagePublisher.sendMessage(message);

      return policyServiceHelper.convertToEntity(persistedDAO);
    }
    else
      throw new Exception("Rule exists for {" + cbaPolicyRule.getStartAge() +" ," + cbaPolicyRule.getEndAge() + " ," + cbaPolicyRule.getContentSet() +"}");

  }

  public Optional<ContentByAgePolicyRule> findContentByAgePolicyRule(UUID id) {
    Optional<ContentByAgePolicyRuleDAO> optionalPolicyRuleDAO =
            contentByAgePolicyRuleRepository.findById(id);

    if(optionalPolicyRuleDAO.isPresent()) {
      ContentByAgePolicyRule policyRule =
              policyServiceHelper.convertToEntity(optionalPolicyRuleDAO.get());

      return Optional.of(policyRule);
    }
    else {
      return Optional.empty();
    }

  }

  public List<ContentByAgePolicyRule> findAllContentByAgePolicyRules() {

    List<ContentByAgePolicyRuleDAO> daoList =
            contentByAgePolicyRuleRepository.findAll();

    return daoList.stream().map(policyServiceHelper::convertToEntity).collect(Collectors.toList());

  }
}
