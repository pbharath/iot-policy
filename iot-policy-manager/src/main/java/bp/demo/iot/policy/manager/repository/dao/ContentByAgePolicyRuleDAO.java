package bp.demo.iot.policy.manager.repository.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table("Content_By_Age_Policy_Rule")
public class ContentByAgePolicyRuleDAO
  implements Serializable {

  @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
  private UUID id;

  @Column(value = "start_age")
  private int startAge;

  @Column(value = "end_age")
  private int endAge;

  @Column(value = "content_types")
  private Set<String> contentSet;

  @Column(value = "created_by")
  private String createdBy;

  @Column(value = "created_timeStamp")
  private Date createdTimeStamp;

  @Column(value = "modified_by")
  private String modifiedBy;

  @Column(value = "modified_timeStamp")
  private Date modifiedTimeStamp;

  public ContentByAgePolicyRuleDAO() {
  }

  public ContentByAgePolicyRuleDAO(UUID id, int startAge, int endAge,
                                   Set<String> contentSet, String createdBy,
                                   Date createdTimeStamp, String modifiedBy,
                                   Date modifiedTimeStamp) {
    this.id = id;
    this.startAge = startAge;
    this.endAge = endAge;
    this.contentSet = contentSet;
    this.createdBy = createdBy;
    this.createdTimeStamp = createdTimeStamp;
    this.modifiedBy = modifiedBy;
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getStartAge() {
    return startAge;
  }

  public void setStartAge(int startAge) {
    this.startAge = startAge;
  }

  public int getEndAge() {
    return endAge;
  }

  public void setEndAge(int endAge) {
    this.endAge = endAge;
  }

  public Set<String> getContentSet() {
    return contentSet;
  }

  public void setContentSet(Set<String> contentSet) {
    this.contentSet = contentSet;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedTimeStamp() {
    return createdTimeStamp;
  }

  public void setCreatedTimeStamp(Date createdTimeStamp) {
    this.createdTimeStamp = createdTimeStamp;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Date getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(Date modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContentByAgePolicyRuleDAO that = (ContentByAgePolicyRuleDAO) o;
    return getStartAge() == that.getStartAge() &&
            getEndAge() == that.getEndAge() &&
            getId().equals(that.getId()) &&
            getContentSet().equals(that.getContentSet()) &&
            getCreatedBy().equals(that.getCreatedBy()) &&
            getCreatedTimeStamp().equals(that.getCreatedTimeStamp()) &&
            getModifiedBy().equals(that.getModifiedBy()) &&
            getModifiedTimeStamp().equals(that.getModifiedTimeStamp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStartAge(), getEndAge(), getContentSet(),
            getCreatedBy(), getCreatedTimeStamp(), getModifiedBy(), getModifiedTimeStamp());
  }

  @Override
  public String toString() {
    return "ContentByAgePolicyRuleDAO{" +
            "id=" + id +
            ", startAge=" + startAge +
            ", endAge=" + endAge +
            ", contentSet=" + contentSet +
            ", createdBy='" + createdBy + '\'' +
            ", createdTimeStamp=" + createdTimeStamp +
            ", modifiedBy='" + modifiedBy + '\'' +
            ", modifiedTimeStamp=" + modifiedTimeStamp + '}';
  }
}
