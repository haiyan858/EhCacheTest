package com.chy.manager.service;

import com.chy.manager.entity.User;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-13 22:03
 * @Description:
 */
public interface EhcacheService {
    // 测试失效情况，有效期为5秒
    public String getTimestamp(String param);

    public String getDataFromDB(String key);

    public void removeDataAtDB(String key);

    public String refreshData(String key);

    public User findById(String userId);

    public boolean isReserved(String userId);

    public void removeUser(String userId);

    public void removeAllUser();

}
