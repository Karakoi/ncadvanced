package com.overseer.service.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.dao.impl.HistoryDaoImpl;
import com.overseer.model.History;
import com.overseer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link HistoryService} interface.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final HistoryDAO historyDAO;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<History> findHistory(Long entityId) {
        Assert.notNull(entityId, "parent request must not be null");
        List<History> histories = historyDAO.findAllForEntity(entityId);
        log.debug("Fetched {} history records for entity with id: {}", histories.size(), entityId);
        return histories;
    }
}
