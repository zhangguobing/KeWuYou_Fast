package com.weiwoju.kewuyou.model.bean;

import java.util.List;

/**
 * Created by zhangguobing on 2017/5/6.
 */
public class PlaceOrderResult {

    /**
     * errcode : 0
     * errmsg : success
     * no : B12017050614390253573
     * order_detail : {"no":"B12017050614390253573","price":30,"original_price":30,"surplus_price":30,"payed_price":"0","coupon_price":"0","online_pay_price":"0","cash_pay_price":"0","wallet_pay_price":"0","debt_price":"0","clean_fraction_price":"0","discount_price":"0","create_time":"2017-05-06 14:39:02","prolist":[{"proid":"444107","style_id":"0","num":"1.00","price":"30.00","name":"きむら川","remark":""}]}
     */

    private String errcode;
    private String errmsg;
    private String no;
    /**
     * no : B12017050614390253573
     * price : 30
     * original_price : 30
     * surplus_price : 30
     * payed_price : 0
     * coupon_price : 0
     * online_pay_price : 0
     * cash_pay_price : 0
     * wallet_pay_price : 0
     * debt_price : 0
     * clean_fraction_price : 0
     * discount_price : 0
     * create_time : 2017-05-06 14:39:02
     * prolist : [{"proid":"444107","style_id":"0","num":"1.00","price":"30.00","name":"きむら川","remark":""}]
     */

    private OrderDetailBean order_detail;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public OrderDetailBean getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(OrderDetailBean order_detail) {
        this.order_detail = order_detail;
    }

    public static class OrderDetailBean {
        private String no;
        private float price;
        private float original_price;
        private float surplus_price;
        private String payed_price;
        private String coupon_price;
        private String online_pay_price;
        private String cash_pay_price;
        private String wallet_pay_price;
        private String debt_price;
        private String clean_fraction_price;
        private String discount_price;
        private String create_time;
        /**
         * proid : 444107
         * style_id : 0
         * num : 1.00
         * price : 30.00
         * name : きむら川
         * remark :
         */

        private List<ProlistBean> prolist;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(float original_price) {
            this.original_price = original_price;
        }

        public float getSurplus_price() {
            return surplus_price;
        }

        public void setSurplus_price(float surplus_price) {
            this.surplus_price = surplus_price;
        }

        public String getPayed_price() {
            return payed_price;
        }

        public void setPayed_price(String payed_price) {
            this.payed_price = payed_price;
        }

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getOnline_pay_price() {
            return online_pay_price;
        }

        public void setOnline_pay_price(String online_pay_price) {
            this.online_pay_price = online_pay_price;
        }

        public String getCash_pay_price() {
            return cash_pay_price;
        }

        public void setCash_pay_price(String cash_pay_price) {
            this.cash_pay_price = cash_pay_price;
        }

        public String getWallet_pay_price() {
            return wallet_pay_price;
        }

        public void setWallet_pay_price(String wallet_pay_price) {
            this.wallet_pay_price = wallet_pay_price;
        }

        public String getDebt_price() {
            return debt_price;
        }

        public void setDebt_price(String debt_price) {
            this.debt_price = debt_price;
        }

        public String getClean_fraction_price() {
            return clean_fraction_price;
        }

        public void setClean_fraction_price(String clean_fraction_price) {
            this.clean_fraction_price = clean_fraction_price;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<ProlistBean> getProlist() {
            return prolist;
        }

        public void setProlist(List<ProlistBean> prolist) {
            this.prolist = prolist;
        }

        public static class ProlistBean {
            private String id;
            private String proid;
            private String style_id;
            private String num;
            private String price;
            private String name;
            private String remark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public String getStyle_id() {
                return style_id;
            }

            public void setStyle_id(String style_id) {
                this.style_id = style_id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
