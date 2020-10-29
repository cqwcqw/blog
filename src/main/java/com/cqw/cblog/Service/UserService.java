package com.cqw.cblog.Service;

import com.cqw.cblog.pojo.User;

public interface UserService {
    User checkUser(String username,String password);
}
