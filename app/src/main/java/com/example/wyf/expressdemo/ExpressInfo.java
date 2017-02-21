package com.example.wyf.expressdemo;

import java.util.List;

/**
 * Created by WYF on 2017/2/21.
 */

public class ExpressInfo {


    /**
     * resultcode : 200
     * reason : 查询物流信息成功
     * result : {"company":"韵达","com":"yd","no":"3809010196532","status":"0","list":[{"datetime":"2017-02-18 17:24:22","remark":"到达：广东珠海公司 已收件","zone":""},{"datetime":"2017-02-18 20:40:09","remark":"到达：广东珠海公司 发往：广东广州网点包","zone":""},{"datetime":"2017-02-18 21:46:51","remark":"到达：广东珠海公司 上级站点：","zone":""},{"datetime":"2017-02-19 04:14:09","remark":"到达：广东广州分拨中心 上级站点：广东珠海公司","zone":""},{"datetime":"2017-02-19 04:22:38","remark":"到达：广东广州分拨中心 发往：广东广州天河区天平架长湴公司","zone":""},{"datetime":"2017-02-19 10:38:43","remark":"到达：广东广州天河区天平架长湴公司 发往：广东广州天河区天平架长湴公司元岗分部","zone":""},{"datetime":"2017-02-21 10:14:14","remark":"到达：广东广州天河区天平架长湴公司元岗分部 指定：大叔(13760629801) 派送","zone":""},{"datetime":"2017-02-21 10:14:44","remark":"到达：广东广州天河区天平架长湴公司元岗分部","zone":""}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * company : 韵达
     * com : yd
     * no : 3809010196532
     * status : 0
     * list : [{"datetime":"2017-02-18 17:24:22","remark":"到达：广东珠海公司 已收件","zone":""},{"datetime":"2017-02-18 20:40:09","remark":"到达：广东珠海公司 发往：广东广州网点包","zone":""},{"datetime":"2017-02-18 21:46:51","remark":"到达：广东珠海公司 上级站点：","zone":""},{"datetime":"2017-02-19 04:14:09","remark":"到达：广东广州分拨中心 上级站点：广东珠海公司","zone":""},{"datetime":"2017-02-19 04:22:38","remark":"到达：广东广州分拨中心 发往：广东广州天河区天平架长湴公司","zone":""},{"datetime":"2017-02-19 10:38:43","remark":"到达：广东广州天河区天平架长湴公司 发往：广东广州天河区天平架长湴公司元岗分部","zone":""},{"datetime":"2017-02-21 10:14:14","remark":"到达：广东广州天河区天平架长湴公司元岗分部 指定：大叔(13760629801) 派送","zone":""},{"datetime":"2017-02-21 10:14:44","remark":"到达：广东广州天河区天平架长湴公司元岗分部","zone":""}]
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String company;
        private String com;
        private String no;
        private String status;
        /**
         * datetime : 2017-02-18 17:24:22
         * remark : 到达：广东珠海公司 已收件
         * zone :
         */

        private List<ListBean> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String datetime;
            private String remark;
            private String zone;

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}
