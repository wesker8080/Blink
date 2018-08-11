package com.eliteai.blink.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MR.ZHANG
 * @create 2018-08-09 15:23
 */
public class UserLocationModel {
    private List<DevListBean> data;



    public List<DevListBean> getData() {
        return data;
    }

    public void setData(List<DevListBean> mData) {
        data = mData;
    }

    @Override
    public String toString() {
        return "UserLocationModel{" +
                ", data=" + data +
                '}';
    }

    public static class DevListBean implements Serializable {
        private String latitude;
        private String longitude;
        private Integer devUserid;

        public Integer getDevUserid() {
            return devUserid;
        }

        public void setDevUserid(Integer mDevUserid) {
            devUserid = mDevUserid;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String mLatitude) {
            latitude = mLatitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String mLongitude) {
            longitude = mLongitude;
        }
        @Override
        public String toString() {
            return "DevListBean{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", devUserid=" + devUserid +
                    '}';
        }
    }
}
