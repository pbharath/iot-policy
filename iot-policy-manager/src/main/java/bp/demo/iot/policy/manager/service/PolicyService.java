package bp.demo.iot.policy.manager.service;

import bp.demo.iot.policy.kafka.model.ContentByAgeData;
import bp.demo.iot.policy.kafka.model.TowerCarrierPlatformData;
import bp.demo.iot.policy.kafka.publisher.ContentByAgeDataMessagePublisher;
import bp.demo.iot.policy.kafka.publisher.TowerCarrierPlatformDataMessagePublisher;
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
import java.util.UUID;

@Service
public class PolicyService
  implements Serializable {

  @Autowired
  private TowerCarrierPlatformPolicyRuleRepository towerCarrierPlatformPolicyRuleRepository;

  @Autowired
  private ContentByAgePolicyRuleRepository contentByAgePolicyRuleRepository;

//  @Autowired
//  private TowerCarrierPlatformDataConsumer towerCarrierPlatformDataConsumer;

  @Autowired
  private TowerCarrierPlatformDataMessagePublisher towerCarrierPlatformDataMessagePublisher;

//  @Autowired
//  private ContentByAgeDataConsumer contentByAgeDataConsumer;

  @Autowired
  private ContentByAgeDataMessagePublisher contentByAgeDataMessagePublisher;

  @Autowired
  private PolicyServiceHelper policyServiceHelper;

  public TowerCarrierPlatformPolicyRule
    createNewTowerCarrierPlatformPolicyRule(TowerCarrierPlatformPolicyRule tcppr)
          throws Exception {

    List<TowerCarrierPlatformPolicyRuleDAO> policyRuleDAOList =
            towerCarrierPlatformPolicyRuleRepository
                    .findByCompositePrimaryKey(
                            tcppr.getTowerName(),
                            tcppr.getCarrierName(),
                            tcppr.getPlatformName());

    if(policyRuleDAOList.size() == 0) {

      TowerCarrierPlatformPolicyRuleDAO oldDAO =
              policyServiceHelper.convertToDAO(tcppr);
      oldDAO.setCreatedTimeStamp(new Date());

      TowerCarrierPlatformPolicyRuleDAO newDAO =
              towerCarrierPlatformPolicyRuleRepository.insert(oldDAO);

      TowerCarrierPlatformData tcpd = new TowerCarrierPlatformData(
              newDAO.getKeyDAO().getTowerName(),
              newDAO.getKeyDAO().getCarrierName(),
              newDAO.getKeyDAO().getPlatformName(),
              newDAO.getActive()
      );

      Message<TowerCarrierPlatformData> message =
              MessageBuilder.withPayload(tcpd)
              .setHeader(KafkaHeaders.MESSAGE_KEY, newDAO.getKeyDAO().getTowerName())
              .setHeader(KafkaHeaders.TOPIC, towerCarrierPlatformDataMessagePublisher.getTopic())
              .build();
      towerCarrierPlatformDataMessagePublisher.sendMessage(message);
//      towerCarrierPlatformDataConsumer.getTowerCarrierPlatformDataLatch().await(10,
//              TimeUnit.SECONDS);

      return policyServiceHelper.convertToEntity(newDAO);
    }
    else
      throw new Exception("Rule exists for {" + tcppr.getTowerName() +", " + tcppr.getCarrierName() + ", " + tcppr.getPlatformName() +"}");

  }

  public ContentByAgePolicyRule createNewContentByAgePolicyRule(ContentByAgePolicyRule cbapr)
    throws Exception {

    List<ContentByAgePolicyRuleDAO> policyRuleDAOList =
      contentByAgePolicyRuleRepository.findByAgeRange(
              cbapr.getStartAge(), cbapr.getEndAge());

    if(policyRuleDAOList.size() == 0) {

      ContentByAgePolicyRuleDAO oldDAO =
              policyServiceHelper.convertToDAO(cbapr);
      oldDAO.setId(UUID.randomUUID());
      oldDAO.setCreatedTimeStamp(new Date());

      ContentByAgePolicyRuleDAO persistedDAO =
              contentByAgePolicyRuleRepository.insert(oldDAO);

      ContentByAgeData cyad = new ContentByAgeData(
              persistedDAO.getId(),
              persistedDAO.getStartAge(),
              persistedDAO.getEndAge(),
              persistedDAO.getContentSet()
      );

      Message<ContentByAgeData> message =
              MessageBuilder.withPayload(cyad)
                      .setHeader(KafkaHeaders.MESSAGE_KEY,
                              persistedDAO.getId().toString())
                      .setHeader(KafkaHeaders.TOPIC,
                              contentByAgeDataMessagePublisher.getTopic())
                      .build();
      contentByAgeDataMessagePublisher.sendMessage(message);
//      contentByAgeDataConsumer.getContentByAgeDataLatch().await(10,
//              TimeUnit.SECONDS);

      return policyServiceHelper.convertToEntity(persistedDAO);
    }
    else
      throw new Exception("Rule exists for {" + cbapr.getStartAge() +" ," + cbapr.getEndAge() + " ," + cbapr.getContentSet() +"}");
  }
}
