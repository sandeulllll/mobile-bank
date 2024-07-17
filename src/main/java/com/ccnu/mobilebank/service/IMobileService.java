package com.ccnu.mobilebank.service;

import com.ccnu.mobilebank.pojo.Mobile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface IMobileService extends IService<Mobile> {
    void addMobile(Mobile mobile);

    void updateMobilePassword(Mobile mobile);

    String login(Mobile mobile) throws Exception;
}
