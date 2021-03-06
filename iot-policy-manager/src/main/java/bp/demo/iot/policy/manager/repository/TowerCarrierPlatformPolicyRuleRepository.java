package bp.demo.iot.policy.manager.repository;

import bp.demo.iot.policy.manager.repository.dao.TowerCarrierPlatformPolicyRuleDAO;
import bp.demo.iot.policy.manager.repository.pk.TowerCarrierPlatformPolicyRuleKeyDAO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TowerCarrierPlatformPolicyRuleRepository
        extends CassandraRepository<TowerCarrierPlatformPolicyRuleDAO, TowerCarrierPlatformPolicyRuleKeyDAO> {

  @Query("SELECT * FROM IotPolicySpace.Tower_Carrier_Platform_Policy_Rule WHERE tower_name=?0 AND carrier_name=?1 " + "AND platform_name=?2")
  Optional<TowerCarrierPlatformPolicyRuleDAO> findByCompositePrimaryKey(final String tower_name, final String carrier_name, final String platform_name);

  @Query("SELECT * FROM IotPolicySpace.Tower_Carrier_Platform_Policy_Rule WHERE id=?0")
  Optional<TowerCarrierPlatformPolicyRuleDAO> findById(final UUID id);

}
