package com.edu.nju.tickets.util;

public class Config {

    public static class ORDER_TYPE {
        //订单类型
        public static final String SEATED = "seated";   //选座购买
        public static final String UNSEATED = "unseated"; //立即购买
    }

    public static class PROJECT_TYPE {
        //计划类型
        public static final String CONCERT = "concert";
        public static final String DANCE = "dance";
        public static final String DRAMA = "drama";
        public static final String SPORT = "sport";
    }

    public static class PAYMENT_CONDITION {
        //支付状态
        public static final String PREPAID = "prepaid"; //已支付
        public static final String UNPAID = "unpaid"; //未支付
    }

    public static class ORDER_CONDITION {
        //订单状态
        public static final String BOOKED = "booked"; // 下单
        public static final String CANCELED = "canceled"; // 取消
        public static final String DEBOOKED = "debooked";
        public static final String COMPLETED = "completed"; // 完成
        public static final String OVERTIME = "overtime"; // 超时
    }

    public static class ORDER_SETTLE_CONDITION {
        public static final String UNSETTLED = "unsettled";
        public static final String SETTLED = "settled";
    }

    public static class TICKET_STATE {
        public static final String CHECKED = "checked";
        public static final String UNCHECKED = "unchecked";
    }

    public static class SEAT_STATE {
        //座位状态
        public static final String BOOKED = "booked"; // 已预定
        public static final String IDLE = "idle"; // 空闲的
    }

    public static class TRANSACTION_CONDITION {
        public static final String UNDERWAY = "underway";
        public static final String COMPLETED = "completed";
        public static final String CANCELLED = "cancelled";
    }

    public static class ACTIVE_STATE {
        public static final String ACTIVE = "active"; // 已激活
        public static final String INACTIVE = "inactive"; // 未激活
        public static final String CANCELLED = "cancelled"; // 已注销
    }

    public static class USER_VERIFY {
        public static final String PWD_ERROR = "password_error"; //密码错误
        public static final String ID_ERROR = "id_not_exist"; //用户不存在
        public static final String PASSED = "correct";
        public static final String NOT_ACTIVE = "not_active";
        public static final String CANCELLED = "cancelled"; //用户已注销
    }

    public static class VENUE_VERIFY {
        public static final String PASSED = "correct";
        public static final String PWD_ERROR = "password_error";
    }

    public static class MANAGER_VERIFY {
        public static final String PASSED = "correct";
        public static final String PWD_ERROR = "password_error";
    }

    public static class VENUE_STATE {
        //场馆信息状态
        public static final String PENDING = "pending";
        public static final String APPROVED = "approved";
        public static final String REJECTED = "rejected";
    }

    public static class VENUE_COMMENT {
        public static final String REGISTER = "register";
        public static final String MODIFY = "modify";
    }

    public static class GENDER {
        public static final String MALE = "male";
        public static final String FEMALE = "female";
    }

    public static class CHANGE_POINTS_TYPE {
        public static final String ADD = "add";
        public static final String SUB = "sub";
    }
}
