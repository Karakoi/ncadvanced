package com.overseer.service.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.dto.HistoryMessageDTO;
import com.overseer.model.History;
import com.overseer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
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
    public List<History> findHistoryList(Long entityId) {
        Assert.notNull(entityId, "id of entity must not be null");
        List<History> histories = historyDAO.findAllForEntity(entityId);
        log.debug("Fetched {} history records for entity with id: {}", histories.size(), entityId);
        return histories;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<HistoryMessageDTO> getHistoryMessageDTOs(Long entityId, int maxNumberOfCharsInText) {
        return convertHistoryInHistoryMessageDTO(findHistoryList(entityId), maxNumberOfCharsInText);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public HistoryMessageDTO createHistoryDtoWithMessages(History history, int maxNumberOfCharsInText) {
        HistoryMessageDTO historyDTO = new HistoryMessageDTO();
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
                } else { // if: estimate time changed
                    text = "Estimate time (in day) was changed from \"" + history.getOldValue() + "\" to \""
                            + history.getNewValue() + "\"";
                }
                break;

            case "description":
                String oldDescription = history.getOldValue();
                String newDescription = history.getNewValue();
                String textWithoutTrimming = "Description was changed from \"" + oldDescription
                        + "\" to \"" + newDescription + "\"";
                if (oldDescription.length() > maxNumberOfCharsInText || newDescription.length() > 0) { // if: we need to trim text
                    historyDTO.setLongMessage(textWithoutTrimming); // long text
                    // trimmed text (below) (will set into historyDTO in the end of the method)
                    text = "Description was changed from \"" + trimText(oldDescription, maxNumberOfCharsInText)
                            + "\" to \"" + trimText(newDescription, maxNumberOfCharsInText) + "\"";
                } else { // if: we do not need to trim text, textWithoutTrimming is our only required text
                    text = textWithoutTrimming;
                }
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
                if (history.getNewValue() == null) { // if: parent id deleted
                    text = "This request was unjoined from \"" + history.getDemonstrationOfOldValue() + "\" request";
                } else { // if: parent id created
                    text = "This request was joined in \"" + history.getDemonstrationOfNewValue() + "\" request";
                }
                break;

            default:
                text = "Some changes";
        }

        historyDTO.setMessage(text);
        return historyDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public HistoryMessageDTO convertHistoryInHistoryMessageDTO(History history, int maxNumberOfCharsInText) {
        HistoryMessageDTO historyMessageDTO = createHistoryDtoWithMessages(history, maxNumberOfCharsInText);
        historyMessageDTO.setId(history.getId());
        historyMessageDTO.setChangerId(history.getChanger().getId());
        historyMessageDTO.setChangerFirstName(history.getChanger().getFirstName());
        historyMessageDTO.setChangerLastName(history.getChanger().getLastName());
        historyMessageDTO.setDateOfChange(history.getDateOfChange());
        return historyMessageDTO;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<HistoryMessageDTO> convertHistoryInHistoryMessageDTO(List<History> histories, int maxNumberOfCharsInText) {
        List<HistoryMessageDTO> historyMessageDTOList = new ArrayList<>();
        for (History history: histories) {
            historyMessageDTOList.add(convertHistoryInHistoryMessageDTO(history, maxNumberOfCharsInText));
        }
        return historyMessageDTOList;
    }

    /**
     * Method trim text to max allowable character number.
     * @param text text that will trim.
     * @param maxCountOfChars max allowable character number.
     * @return trimmed text.
     */
    private String trimText(String text, int maxCountOfChars) {
        return text.length() <= maxCountOfChars ? text : text.substring(0,  maxCountOfChars) + "...";
    }
}
