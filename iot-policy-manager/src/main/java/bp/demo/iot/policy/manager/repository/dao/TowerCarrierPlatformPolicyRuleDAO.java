package bp.demo.iot.policy.manager.repository.dao;

import bp.demo.iot.policy.manager.repository.pk.TowerCarrierPlatformPolicyRuleKeyDAO;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Table("Tower_Carrier_Platform_Policy_Rule")
public class TowerCarrierPlatformPolicyRuleDAO
  implements Serializable {

  @PrimaryKey
  private TowerCarrierPlatformPolicyRuleKeyDAO keyDAO;

  @Column(value = "id")
  private UUID id;

  @Column(value = "is_active")
  private boolean isActive;

  @Column(value = "created_by")
  private String createdBy;

  @Column(value = "created_timeStamp")
  private Date createdTimeStamp;

  @Column(value = "modified_by")
  private String modifiedBy;

  @Column(value = "modified_timeStamp")
  private Date modifiedTimeStamp;


  public TowerCarrierPlatformPolicyRuleDAO(){}

  public TowerCarrierPlatformPolicyRuleDAO(TowerCarrierPlatformPolicyRuleKeyDAO keyDAO, UUID id, boolean isActive, String createdBy, Date createdTimeStamp, String modifiedBy, Date modifiedTimeStamp) {
    this.keyDAO = keyDAO;
    this.id = id;
    this.isActive = isActive;
    this.createdBy = createdBy;
    this.createdTimeStamp = createdTimeStamp;
    this.modifiedBy = modifiedBy;
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public TowerCarrierPlatformPolicyRuleKeyDAO getKeyDAO() {
    return keyDAO;
  }

  public void setKeyDAO(TowerCarrierPlatformPolicyRuleKeyDAO keyDAO) {
    this.keyDAO = keyDAO;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public boolean getActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
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
    TowerCarrierPlatformPolicyRuleDAO that =
            (TowerCarrierPlatformPolicyRuleDAO) o;
    return getActive() == that.getActive() &&
            getKeyDAO().equals(that.getKeyDAO()) &&
            getCreatedTimeStamp().equals(that.getCreatedTimeStamp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKeyDAO(), getActive(), getCreatedTimeStamp());
  }

  @Override
  public String toString() {
    return "TowerCarrierPlatformPolicyRuleDAO{" + "keyDAO=" + keyDAO + ", " +
            "isActive=" + isActive + ", id=" + id + ", createdBy='" + createdBy + '\'' + ", createdTimeStamp=" + createdTimeStamp + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedTimeStamp=" + modifiedTimeStamp + '}';
  }
}
