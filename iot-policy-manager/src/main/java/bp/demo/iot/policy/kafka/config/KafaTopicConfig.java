package bp.demo.iot.policy.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = {"classpath:iot-policy-kafka.properties"})
public class KafaTopicConfig {

  @Value(value = "${bp.demo.iot.policy.kafka.brokerlist}")
  private String bootstrapAddress;

  @Value(value = "${bp.demo.iot.policy.kafka.data.age.content.topic}")
  private String ageContentTopicName;

  @Value(value = "${bp.demo.iot.policy.kafka.data.tower.carrier.platform.topic}")
  private String towerCarrierPlatformTopicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic ageContentTopic() {
    return new NewTopic(ageContentTopicName, 1, (short) 1);
  }

  @Bean
  public NewTopic towerCarrierPlatformTopic() {
    return new NewTopic(towerCarrierPlatformTopicName, 1, (short) 1);
  }

}
