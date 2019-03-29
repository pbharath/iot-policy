package bp.demo.iot.policy.manager.repository;

import bp.demo.iot.policy.manager.repository.dao.ContentByAgePolicyRuleDAO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface ContentByAgePolicyRuleRepository
  extends CassandraRepository<ContentByAgePolicyRuleDAO, UUID> {

  @Query("SELECT * FROM IotPolicySpace.Content_By_Age_Policy_Rule " + "WHERE " +
          "start_age>=?0 AND end_age<=?1")
  Stream<ContentByAgePolicyRuleDAO> findByAgeRange(int startAge, int endAge);

  @Query("SELECT * FROM IotPolicySpace.Content_By_Age_Policy_Rule WHERE id=?0")
  Optional<ContentByAgePolicyRuleDAO> findById(final UUID id);
}
