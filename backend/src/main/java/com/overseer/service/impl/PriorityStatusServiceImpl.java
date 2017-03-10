package com.overseer.service.impl;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import com.overseer.service.PriorityStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link PriorityStatusService} interface.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PriorityStatusServiceImpl implements PriorityStatusService {
    private final PriorityStatusDao priorityStatusDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<PriorityStatus> findAllPriorityStatuses() {
        val list = priorityStatusDao.findAll();
        log.debug("Fetched {} priority statuses", list.size());
        return list;
    }
}