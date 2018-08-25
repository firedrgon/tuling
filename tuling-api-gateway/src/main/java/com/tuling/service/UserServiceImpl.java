package com.tuling.service;

import com.tuling.core.ApiMapping;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

// DEMO
@Service
public class UserServiceImpl {
    // 无缝集成
    @ApiMapping(value = "bit.api.user.getUser",userLogin = true)
    public UserInfo getUser(Long userId) {
        Assert.notNull(userId);
        UserInfo info = new UserInfo();
        info.setName("小明");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        if (info.getSex().equals("男0")) {
            // throw new Exception()
        }
        return info;
    }

    /**
     * 获取用户所有的订单信息
     * @param beforeTime
     */
    @ApiMapping(value = "bit.api.user.getUser")
    public void getOrders(Long beforeTime, ApiMapping request){

    }

    @ApiMapping("bit.api.user.getUser2")
    public UserInfo getUser4(Long userId) {
        Assert.notNull(userId);
        UserInfo info = new UserInfo();
        info.setName("小明");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        if (info.getSex().equals("男0")) {
            // throw new Exception()
        }

        return info;
    }

    @ApiMapping("bit.api.user.getUser2")
    public UserInfo getUser2(Long userId) {
        Assert.notNull(userId);

        UserInfo info = new UserInfo();
        info.setName("小明2");
        info.setSex("男");
        info.setUserId(userId);
        info.setIdcard("430527198108145443");
        return info;
    }
}
