package com.ccnu.mobilebank.service;

import com.ccnu.mobilebank.pojo.Personinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Baomidou
 * @since 2024-07-16
 */
public interface IPersoninfoService extends IService<Personinfo> {

    Personinfo getPersonInfoByTel(String telephone);
}
