package com.overseer.util;

import com.overseer.model.ProgressStatusView;
import com.overseer.model.enums.ProgressStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation.
 */
@Slf4j
@Component
@Scope("singleton")
public class ProgressStatusUtil {

    private static final int FREE_ID = 5;
    private static final int JOINED_ID = 6;
    private static final int IN_PROGRESS_ID = 7;
    private static final int CLOSED_ID = 8;
    private List<ProgressStatusView> viewsCollection;

    ProgressStatusUtil() {
        init();
    }

    /**
     * Initialization collection of ProgressStatusView.
     */
    private void init() {
        this.viewsCollection = new ArrayList<>();
        ProgressStatusView free = new ProgressStatusView();
        free.setId(com.overseer.model.enums.ProgressStatus.FREE.getId());
        free.setName(com.overseer.model.enums.ProgressStatus.FREE.name());
        free.setValue(com.overseer.model.enums.ProgressStatus.FREE.getValue());
        viewsCollection.add(free);

        ProgressStatusView joined = new ProgressStatusView();
        joined.setId(com.overseer.model.enums.ProgressStatus.JOINED.getId());
        joined.setName(com.overseer.model.enums.ProgressStatus.JOINED.name());
        joined.setValue(com.overseer.model.enums.ProgressStatus.JOINED.getValue());
        viewsCollection.add(joined);

        ProgressStatusView inProgress = new ProgressStatusView();
        inProgress.setId(com.overseer.model.enums.ProgressStatus.IN_PROGRESS.getId());
        inProgress.setName(com.overseer.model.enums.ProgressStatus.IN_PROGRESS.name());
        inProgress.setValue(com.overseer.model.enums.ProgressStatus.IN_PROGRESS.getValue());
        viewsCollection.add(inProgress);

        ProgressStatusView closed = new ProgressStatusView();
        closed.setId(com.overseer.model.enums.ProgressStatus.CLOSED.getId());
        closed.setName(com.overseer.model.enums.ProgressStatus.CLOSED.name());
        closed.setValue(com.overseer.model.enums.ProgressStatus.CLOSED.getValue());
        viewsCollection.add(closed);
    }


    /**
     * Returns collection of ProgressStatusView.
     *
     * @return list of ProgressStatusView.
     */
    public List<ProgressStatusView> getAllProgressStatusViews() {
        log.debug("Fetched {} progress statuses", viewsCollection.size());
        return viewsCollection;
    }

    /**
     * Returns collection of ProgressStatusView.
     *
     * @return list of ProgressStatusView.
     */
    public ProgressStatus getProgressByProgressView(ProgressStatusView progressStatusView) {
        return getProgressById(progressStatusView.getId());
    }

    /**
     * Returns progress status by id.
     *
     * @param id id of progress status.
     * @return progress status by id.
     */
    public ProgressStatus getProgressById(long id) {
        ProgressStatus progressStatus;
        switch ((int) id) {
            case FREE_ID:
                progressStatus = ProgressStatus.FREE;
                break;
            case JOINED_ID:
                progressStatus = ProgressStatus.JOINED;
                break;
            case IN_PROGRESS_ID:
                progressStatus = ProgressStatus.IN_PROGRESS;
                break;
            case CLOSED_ID:
                progressStatus = ProgressStatus.CLOSED;
                break;
            default:
                progressStatus = null;
                break;
        }
        return progressStatus;
    }

}
