package com.cqw.cblog.Service;

import com.cqw.cblog.DAO.UserRepository;
import com.cqw.cblog.Utils.MD5Utils;
import com.cqw.cblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplement implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, new MD5Utils().EncodeByMd5(password));
        return user;
    }
}
