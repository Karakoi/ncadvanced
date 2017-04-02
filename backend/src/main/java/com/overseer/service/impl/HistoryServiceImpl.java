package com.overseer.service.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.model.History;
import com.overseer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Assert.notNull(entityId, "id of entity must not be null");
        List<History> histories = historyDAO.findAllForEntity(entityId);
        log.debug("Fetched {} history records for entity with id: {}", histories.size(), entityId);
        return histories;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String createMessageFromChanges(History history, boolean useTrimLongText, int maxNumberOfCharsInText) {
        String text;
        switch (history.getColumnName()) {

            case "title":
                text = "Title was changed from \"" + history.getOldValue() + "\" to \"" + history.getNewValue() + "\"";
                break;

            case "estimate_time_in_days":
                if (history.getOldValue() == null) { // if: estimate time set
                    text = "Estimate time (in day) was set in \"" + history.getNewValue() + "\" days";
                } else if (history.getNewValue() == null) { // if: estimate time deleted
                    text = "Estimate time (in day) was deleted";
                } else { // if: change estimate time
                    text = "Estimate time (in day) was changed from \"" + history.getOldValue() + "\" to \"" + history.getNewValue() + "\"";
                }
                break;

            case "description":
                String oldDescription = history.getDemonstrationOfOldValue();
                String newDescription = history.getDemonstrationOfNewValue();
                if (useTrimLongText) { // if text must be trimmed
                    trimText(oldDescription, maxNumberOfCharsInText);
                    trimText(newDescription, maxNumberOfCharsInText);
                }
                text = "Description was changed from \"" + oldDescription + "\" to \"" + newDescription + "\"";
                break;

            case "priority_status_id":
                text = "Priority was changed from \""
                        + history.getDemonstrationOfOldValue() + "\" to \"" + history.getDemonstrationOfNewValue() + "\"";
                break;

            case "progress_status_id":
                text = "Progress status was changed from \""
                        + history.getDemonstrationOfOldValue() + "\" to \"" + history.getDemonstrationOfNewValue() + "\"";
                break;

            case "assignee_id":
                if (history.getNewValue() == null) { // if: assignee deleted
                    text = "This request was unassigned";
                } else { // if: assignee appeared
                    text = "This request was assigned";
                }
                break;

            case "parent_id":
                if (history.getNewValue() == null) {
                    text = "This request was unjoined from \"" + history.getDemonstrationOfOldValue() + "\" request";
                } else {
                    text = "This request was joined in \"" + history.getDemonstrationOfNewValue() + "\" request";
                }
                break;

            default:
                text = "Some changes";
        }

        return text;
    }

    /**
     * Method trim text to max allowable character number.
     * @param text text that will trim.
     * @param maxCountOfChars max allowable character number.
     * @return trimmed text.
     */
    private String trimText(String text, int maxCountOfChars) {
        if (text.length() > maxCountOfChars) {
            text = text.substring(0,  maxCountOfChars) + "...";
        }
        return text;
    }
}
