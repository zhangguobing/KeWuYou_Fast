package com.weiwoju.kewuyou.model.bean;

import java.util.List;

/**
 * Created by zhangguobing on 2017/4/26.
 */
public class LoginResult extends ResponseBean{

    private String session_id;
    private String tel;
    private List<ShopBean> shoplist;
    private String ad_url;
    private String industry;
    private List<DiscountBean> discount;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<ShopBean> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<ShopBean> shoplist) {
        this.shoplist = shoplist;
    }

    public String getAd_url() {
        return ad_url;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<DiscountBean> getDiscount() {
        return discount;
    }

    public void setDiscount(List<DiscountBean> discount) {
        this.discount = discount;
    }


    public static class ShopBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DiscountBean {
        private String id;
        private float discount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public float getDiscount() {
            return discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }
    }
}
