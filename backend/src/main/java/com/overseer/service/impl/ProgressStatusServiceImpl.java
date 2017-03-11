package com.overseer.service.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import com.overseer.service.ProgressStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ProgressStatusService} interface.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProgressStatusServiceImpl implements ProgressStatusService {
    private final ProgressStatusDao progressStatusDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ProgressStatus> findAllProgressStatuses() {
        val list = progressStatusDao.findAll();
        log.debug("Fetched {} progress statuses", list.size());
        return list;
    }
}
