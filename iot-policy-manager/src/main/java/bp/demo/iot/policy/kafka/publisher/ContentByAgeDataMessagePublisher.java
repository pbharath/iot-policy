package bp.demo.iot.policy.kafka.publisher;

import bp.demo.iot.policy.kafka.model.ContentByAgeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ContentByAgeDataMessagePublisher {

  @Autowired
  private KafkaTemplate<String, ContentByAgeData> contentByAgeDataKafkaTemplate;

  @Value(value = "${bp.demo.iot.policy.kafka.data.age.content.topic}")
  private String contentByAgeDataTopic;

  public void sendCarrierEventMessage(ContentByAgeData contentByAgeData) {
    contentByAgeDataKafkaTemplate.send(contentByAgeDataTopic, contentByAgeData);
  }

  public String getTopic() {
    return this.contentByAgeDataTopic;
  }

  public void sendMessage(Message message) {

    ListenableFuture<SendResult<String, ContentByAgeData>> future = contentByAgeDataKafkaTemplate.send(message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, ContentByAgeData>>() {

      @Override
      public void onSuccess(SendResult<String, ContentByAgeData> result) {
        System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
      }
    });
  }

}
