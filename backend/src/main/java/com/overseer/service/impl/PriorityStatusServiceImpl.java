package com.overseer.service.impl;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import com.overseer.service.PriorityStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link PriorityStatusService} interface.
 */
@Service
@RequiredArgsConstructor
public class PriorityStatusServiceImpl implements PriorityStatusService {

    private final PriorityStatusDao priorityStatusDao;

    private static final Logger LOG = LoggerFactory.getLogger(PriorityStatusServiceImpl.class);

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<PriorityStatus> findAllPriorityStatuses() {
        LOG.debug("Fetching all priority statuses values");
        return priorityStatusDao.findAll();
    }
}