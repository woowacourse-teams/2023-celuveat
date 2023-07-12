package com.celuveat.celuveat.admin.infra.persistence;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.exception.AdminException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.HashMap;
import java.util.Map;

public class FakeAdminDao extends AdminDao {

    private final Map<Long, Admin> store = new HashMap<>();
    private long id = 1L;

    public FakeAdminDao() {
        super(mock(JdbcTemplate.class));
    }

    @Override
    public Long save(Admin admin) {
        store.put(id, admin);
        return id++;
    }

    @Override
    public Admin getByUsername(String username) {
        return store.values().stream()
                .filter(admin -> admin.username().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new AdminException(NOT_FOUND_ADMIN));
    }
}
