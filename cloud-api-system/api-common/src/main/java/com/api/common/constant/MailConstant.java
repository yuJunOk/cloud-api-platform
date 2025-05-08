package com.api.common.constant;

import static com.api.common.constant.SystemConstant.SYSTEM_NAME;

/**
 * @author pengYuJun
 */
public interface MailConstant {
    /**
     * 管理员邮箱，bug提醒用
     */
    String MANAGE_MAIL_ADDRESS ="1375841038@qq.com";

    /**
     * bug发送标题
     */
    String BUG_MAIL_TITLE = SYSTEM_NAME + "Bug提醒";

    /**
     * 验证码发送标题
     */
    String CAPTCHA_MAIL_TITLE = SYSTEM_NAME + "自定义管理系统验证码";

}
