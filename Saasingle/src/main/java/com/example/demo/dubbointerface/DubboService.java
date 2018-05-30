package com.example.demo.dubbointerface;

import com.example.demo.domain.User;
import java.util.List;

public interface DubboService {
    int insert(User user);
    List<User> search(String username);
}
