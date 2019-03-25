package bp.demo.iot.policy.manager.service.helper;

import bp.demo.iot.policy.manager.model.ContentByAgePolicyRule;
import bp.demo.iot.policy.manager.model.TowerCarrierPlatformPolicyRule;
import bp.demo.iot.policy.manager.repository.dao.ContentByAgePolicyRuleDAO;
import bp.demo.iot.policy.manager.repository.dao.TowerCarrierPlatformPolicyRuleDAO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PolicyServiceHelper {

  @Autowired
  private ModelMapper modelMapper;


  public List<TowerCarrierPlatformPolicyRule> mapDAOListToEntityList(
          List<TowerCarrierPlatformPolicyRuleDAO> daoList) {

    return daoList.stream()
            .map(dao -> convertToEntity(dao))
            .collect(Collectors.toList());

  }

  public TowerCarrierPlatformPolicyRuleDAO
    convertToDAO(TowerCarrierPlatformPolicyRule policyRule) {

    PropertyMap<TowerCarrierPlatformPolicyRule,
            TowerCarrierPlatformPolicyRuleDAO> prMap =
            new PropertyMap<TowerCarrierPlatformPolicyRule,
                    TowerCarrierPlatformPolicyRuleDAO>() {
              protected void configure() {
                map().getKeyDAO().setCarrierName(source.getCarrierName());
                map().getKeyDAO().setTowerName(source.getTowerName());
                map().getKeyDAO().setPlatformName(source.getPlatformName());
                map().setActive(source.getActive());
                map().setCreatedBy(source.getCreatedBy());
                map().setCreatedTimeStamp(source.getCreatedTimeStamp());

                if(source.getModifiedBy() != null) {
                  map().setModifiedBy(source.getModifiedBy());
                }

                if(source.getModifiedTimeStamp() != null) {
                  map().setModifiedTimeStamp(source.getModifiedTimeStamp());
                }
              }
            };

    TypeMap<TowerCarrierPlatformPolicyRule, TowerCarrierPlatformPolicyRuleDAO> typeMap
            = modelMapper.getTypeMap(TowerCarrierPlatformPolicyRule.class, TowerCarrierPlatformPolicyRuleDAO.class);
    if (typeMap == null) {
      modelMapper.addMappings(prMap);
    }

    return modelMapper.map(policyRule, TowerCarrierPlatformPolicyRuleDAO.class);
  }

  public TowerCarrierPlatformPolicyRule
    convertToEntity(TowerCarrierPlatformPolicyRuleDAO dao) {

    PropertyMap<TowerCarrierPlatformPolicyRuleDAO,
                TowerCarrierPlatformPolicyRule> prMap =
            new PropertyMap<TowerCarrierPlatformPolicyRuleDAO, TowerCarrierPlatformPolicyRule>() {
      protected void configure() {
        map().setCarrierName(source.getKeyDAO().getCarrierName());
        map().setTowerName(source.getKeyDAO().getTowerName());
        map().setPlatformName(source.getKeyDAO().getPlatformName());
        map().setActive(source.getActive());
        map().setCreatedBy(source.getCreatedBy());
        map().setCreatedTimeStamp(source.getCreatedTimeStamp());

        if(source.getModifiedBy() != null) {
          map().setModifiedBy(source.getModifiedBy());
        }

        if(source.getModifiedTimeStamp() != null) {
          map().setModifiedTimeStamp(source.getModifiedTimeStamp());
        }
      }
    };

    TypeMap<TowerCarrierPlatformPolicyRuleDAO, TowerCarrierPlatformPolicyRule> typeMap
            = modelMapper.getTypeMap(TowerCarrierPlatformPolicyRuleDAO.class, TowerCarrierPlatformPolicyRule.class);
    if (typeMap == null) {
      modelMapper.addMappings(prMap);
    }

    return modelMapper.map(dao, TowerCarrierPlatformPolicyRule.class);

  }

  public ContentByAgePolicyRuleDAO
    convertToDAO(ContentByAgePolicyRule policyRule) {

      PropertyMap<ContentByAgePolicyRule,
              ContentByAgePolicyRuleDAO> prMap =
              new PropertyMap<ContentByAgePolicyRule,
                      ContentByAgePolicyRuleDAO>() {
                protected void configure() {
                  map().setStartAge(source.getStartAge());
                  map().setEndAge(source.getEndAge());
                  map().setContentSet(source.getContentSet());
                  map().setCreatedBy(source.getCreatedBy());
                  map().setCreatedTimeStamp(source.getCreatedTimeStamp());

                  if(source.getModifiedBy() != null) {
                    map().setModifiedBy(source.getModifiedBy());
                  }

                  if(source.getModifiedTimeStamp() != null) {
                    map().setModifiedTimeStamp(source.getModifiedTimeStamp());
                  }
                }
              };

    TypeMap<ContentByAgePolicyRule, ContentByAgePolicyRuleDAO> typeMap
            = modelMapper.getTypeMap(ContentByAgePolicyRule.class, ContentByAgePolicyRuleDAO.class);
    if (typeMap == null) {
      modelMapper.addMappings(prMap);
    }

    return modelMapper.map(policyRule, ContentByAgePolicyRuleDAO.class);
  }

  public ContentByAgePolicyRule
  convertToEntity(ContentByAgePolicyRuleDAO dao) {

    PropertyMap<ContentByAgePolicyRuleDAO,
            ContentByAgePolicyRule> prMap =
            new PropertyMap<ContentByAgePolicyRuleDAO, ContentByAgePolicyRule>() {
              protected void configure() {
                map().setStartAge(source.getStartAge());
                map().setEndAge(source.getEndAge());
                map().setContentSet(source.getContentSet());
                map().setCreatedBy(source.getCreatedBy());
                map().setCreatedTimeStamp(source.getCreatedTimeStamp());

                if(source.getModifiedBy() != null) {
                  map().setModifiedBy(source.getModifiedBy());
                }

                if(source.getModifiedTimeStamp() != null) {
                  map().setModifiedTimeStamp(source.getModifiedTimeStamp());
                }
              }
            };

    TypeMap<ContentByAgePolicyRuleDAO, ContentByAgePolicyRule> typeMap
            = modelMapper.getTypeMap(ContentByAgePolicyRuleDAO.class, ContentByAgePolicyRule.class);
    if (typeMap == null) {
      modelMapper.addMappings(prMap);
    }

    return modelMapper.map(dao, ContentByAgePolicyRule.class);

  }
}
