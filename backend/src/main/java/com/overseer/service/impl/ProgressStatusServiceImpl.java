package com.overseer.service.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import com.overseer.service.ProgressStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ProgressStatusService} interface.
 */
@Service
@RequiredArgsConstructor
public class ProgressStatusServiceImpl implements ProgressStatusService {

    private final ProgressStatusDao progressStatusDao;

    private static final Logger LOG = LoggerFactory.getLogger(ProgressStatusServiceImpl.class);

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ProgressStatus> findAllProgressStatuses() {
        LOG.debug("Fetching all progress statuses values");
        return progressStatusDao.findAll();
    }
}
