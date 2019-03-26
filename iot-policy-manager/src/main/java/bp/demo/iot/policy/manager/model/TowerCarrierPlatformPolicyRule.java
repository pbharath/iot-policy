package bp.demo.iot.policy.manager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class TowerCarrierPlatformPolicyRule
  implements Serializable {

  private UUID id;

  private String towerName;

  private String carrierName;

  private String platformName;

  private boolean active = true;

  private String createdBy;

  private Date createdTimeStamp;

  private String modifiedBy;

  private Date modifiedTimeStamp;

  public TowerCarrierPlatformPolicyRule() {}

  public TowerCarrierPlatformPolicyRule(UUID id, String towerName,
                                        String carrierName, String platformName, boolean active, String createdBy, Date createdTimeStamp, String modifiedBy, Date modifiedTimeStamp) {
    this.id = id;
    this.towerName = towerName;
    this.carrierName = carrierName;
    this.platformName = platformName;
    this.active = active;
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

  public String getTowerName() {
    return towerName;
  }

  public void setTowerName(String towerName) {
    this.towerName = towerName;
  }

  public String getCarrierName() {
    return carrierName;
  }

  public void setCarrierName(String carrierName) {
    this.carrierName = carrierName;
  }

  public String getPlatformName() {
    return platformName;
  }

  public void setPlatformName(String platformName) {
    this.platformName = platformName;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
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
    TowerCarrierPlatformPolicyRule that = (TowerCarrierPlatformPolicyRule) o;
    return getActive() == that.getActive() && getTowerName().equals(that.getTowerName()) && getCarrierName().equals(that.getCarrierName()) && getPlatformName().equals(that.getPlatformName()) && Objects.equals(getId(), that.getId()) && getCreatedBy().equals(that.getCreatedBy()) && getCreatedTimeStamp().equals(that.getCreatedTimeStamp()) && Objects.equals(getModifiedBy(), that.getModifiedBy()) && Objects.equals(getModifiedTimeStamp(), that.getModifiedTimeStamp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTowerName(), getCarrierName(), getPlatformName(),
            getId(), getActive(), getCreatedBy(), getCreatedTimeStamp(),
            getModifiedBy(), getModifiedTimeStamp());
  }

  @Override
  public String toString() {
    return "TowerCarrierPlatformPolicyRule{" + "id=" + id + ", towerName='" + towerName + '\'' + ", carrierName='" + carrierName + '\'' + ", platformName='" + platformName + '\'' + ", active=" + active + ", createdBy='" + createdBy + '\'' + ", createdTimeStamp=" + createdTimeStamp + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedTimeStamp=" + modifiedTimeStamp + '}';
  }
}
