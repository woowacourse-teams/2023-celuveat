package com.celuveat.celuveat.admin.application;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.domain.PasswordEncoder;
import com.celuveat.celuveat.admin.infra.persistence.AdminDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDao adminDao;
    private final PasswordEncoder encoder;

    public Long login(String username, String rawPassword) {
        Admin admin = adminDao.getByUsername(username);
        admin.login(encoder, rawPassword);
        return admin.id();
    }
}
