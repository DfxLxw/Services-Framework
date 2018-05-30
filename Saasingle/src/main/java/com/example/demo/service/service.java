package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface service {
    int insert(User user);
    List<User> search(String username);
}
