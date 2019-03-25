package bp.demo.iot.policy.kafka.publisher;

import bp.demo.iot.policy.kafka.model.TowerCarrierPlatformData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class TowerCarrierPlatformDataMessagePublisher {

  @Autowired
  private KafkaTemplate<String, TowerCarrierPlatformData> towerCarrierPlatformDataKafkaTemplate;

  @Value(value = "${bp.demo.iot.policy.kafka.data.tower.carrier.platform.topic}")
  private String towerCarrierPlatformDataTopic;

  public void sendCarrierEventMessage(TowerCarrierPlatformData towerCarrierPlatformData) {
    towerCarrierPlatformDataKafkaTemplate.send(towerCarrierPlatformDataTopic,
            towerCarrierPlatformData);
  }

  public String getTopic() {
    return this.towerCarrierPlatformDataTopic;
  }

  public void sendMessage(Message message) {

    ListenableFuture<SendResult<String, TowerCarrierPlatformData>> future = towerCarrierPlatformDataKafkaTemplate.send(message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, TowerCarrierPlatformData>>() {

      @Override
      public void onSuccess(SendResult<String, TowerCarrierPlatformData> result) {
        System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
      }
    });
  }
}
