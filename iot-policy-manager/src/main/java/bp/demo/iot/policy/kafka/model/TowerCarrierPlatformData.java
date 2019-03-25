package bp.demo.iot.policy.kafka.model;

import java.io.Serializable;
import java.util.Objects;

public class TowerCarrierPlatformData
 implements Serializable {

  private String towerName;

  private String carrierName;

  private String platformName;

  private boolean active;

  public TowerCarrierPlatformData() {
  }

  public TowerCarrierPlatformData(String towerName, String carrierName,
                                  String platformName, boolean active) {
    this.towerName = towerName;
    this.carrierName = carrierName;
    this.platformName = platformName;
    this.active = active;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TowerCarrierPlatformData that = (TowerCarrierPlatformData) o;
    return isActive() == that.isActive() &&
            getTowerName().equals(that.getTowerName()) &&
            getCarrierName().equals(that.getCarrierName()) &&
            getPlatformName().equals(that.getPlatformName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTowerName(), getCarrierName(), getPlatformName(), isActive());
  }

  @Override
  public String toString() {
    return "TowerCarrierPlatformData{" +
            "towerName='" + towerName + '\'' +
            ", carrierName='" + carrierName + '\'' +
            ", platformName='" + platformName + '\'' +
            ", active=" + active + '}';
  }
}
