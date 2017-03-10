package com.overseer.service.impl;

import com.overseer.dao.RoleDao;
import com.overseer.model.Role;
import com.overseer.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link RoleService} interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl extends CrudServiceImpl<Role> implements RoleService {

    private final RoleDao roleDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Role> findAllRoles() {
        val list = roleDao.findAll();
        log.debug("Fetched {} roles", list.size());
        return list;
    }
}