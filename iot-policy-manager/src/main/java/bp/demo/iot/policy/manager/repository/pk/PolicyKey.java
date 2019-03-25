package bp.demo.iot.policy.manager.repository.pk;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;

import java.io.Serializable;

@PrimaryKeyClass
public class PolicyKey
  implements Serializable {
}
