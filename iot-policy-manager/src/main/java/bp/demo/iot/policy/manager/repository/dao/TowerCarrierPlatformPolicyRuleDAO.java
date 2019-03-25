package bp.demo.iot.policy.manager.repository.dao;

import bp.demo.iot.policy.manager.repository.pk.TowerCarrierPlatformPolicyRuleKeyDAO;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table("Tower_Carrier_Platform_Policy_Rule")
public class TowerCarrierPlatformPolicyRuleDAO
  implements Serializable {

  @PrimaryKey
  private TowerCarrierPlatformPolicyRuleKeyDAO keyDAO;

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

  public TowerCarrierPlatformPolicyRuleDAO(
          TowerCarrierPlatformPolicyRuleKeyDAO keyDAO,
          boolean isActive, String createdBy,
          @PastOrPresent Date createdTimeStamp,
          String modifiedBy,
          @PastOrPresent Date modifiedTimeStamp) {
    this.keyDAO = keyDAO;
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
    return "TowerCarrierPlatformPolicyRuleDAO{" +
            "keyDAO=" + keyDAO + ", " +
            "isActive=" + isActive +
            ", createdBy='" + createdBy + '\'' + ", " +
            "createdTimeStamp=" + createdTimeStamp +
            ", modifiedBy='" + modifiedBy + '\'' +
            ", modifiedTimeStamp=" + modifiedTimeStamp + '}';
  }
}
