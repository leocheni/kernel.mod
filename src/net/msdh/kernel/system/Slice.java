package net.msdh.kernel.system;

import java.security.PrivateKey;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:06
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Slice {
  private String mntNameTo;
  private String mntNameFrom;
  private String totalSize;
  private String usedSize;
  private String freeSizel;
  private String fsType;

    public Slice() {
    }

    public Slice(String mntNameTo, String mntNameFrom, String totalSize, String usedSize, String freeSizel, String fsType) {
        this.mntNameTo = mntNameTo;
        this.mntNameFrom = mntNameFrom;
        this.totalSize = totalSize;
        this.usedSize = usedSize;
        this.freeSizel = freeSizel;
        this.fsType = fsType;
    }

    public String getMntNameTo() {
        return mntNameTo;
    }

    public void setMntNameTo(String mntNameTo) {
        this.mntNameTo = mntNameTo;
    }

    public String getMntNameFrom() {
        return mntNameFrom;
    }

    public void setMntNameFrom(String mntNameFrom) {
        this.mntNameFrom = mntNameFrom;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(String usedSize) {
        this.usedSize = usedSize;
    }

    public String getFreeSize() {
        return freeSizel;
    }

    public void setFreeSize(String freeSizel) {
        this.freeSizel = freeSizel;
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }
}
