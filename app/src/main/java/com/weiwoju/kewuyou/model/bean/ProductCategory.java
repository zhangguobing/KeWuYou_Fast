package com.weiwoju.kewuyou.model.bean;

import java.util.List;

/**
 * Created by zhangguobing on 2017/4/28.
 */
public class ProductCategory extends ResponseBean{

    private List<ProlistBean> prolist;
    private List<CatelistBean> catelist;
    
    public List<ProlistBean> getProlist() {
        return prolist;
    }

    public void setProlist(List<ProlistBean> prolist) {
        this.prolist = prolist;
    }

    public List<CatelistBean> getCatelist() {
        return catelist;
    }

    public void setCatelist(List<CatelistBean> catelist) {
        this.catelist = catelist;
    }

    public static class ProlistBean {
        public static final String DEFAULT_UNIT_NAME = "份";

        private String id;
        private String name;
        private String price;
        private String cate_id;
        private String unit_name = DEFAULT_UNIT_NAME;
        private String stock_num;
        private List<StyleBean> style_list;
        private int style_id;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getStock_num() {
            return stock_num;
        }

        public void setStock_num(String stock_num) {
            this.stock_num = stock_num;
        }

        public List<StyleBean> getStyle_list() {
            return style_list;
        }

        public void setStyle_list(List<StyleBean> style_list) {
            this.style_list = style_list;
        }

        public int getStyle_id() {
            return style_id;
        }

        public void setStyle_id(int style_id) {
            this.style_id = style_id;
        }
    }

    public static class CatelistBean {
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

    public static class StyleBean {
        private String id;
        private String name;
        private String price;
        private String stock_sum;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStock_sum() {
            return stock_sum;
        }

        public void setStock_sum(String stock_sum) {
            this.stock_sum = stock_sum;
        }
    }
}
