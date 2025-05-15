package tasks.services;

import java.util.Date;

public class Task {
    private final String title;
    private final Date startDate;
    private final Date endDate;
    private final Integer interval;
    private boolean isActive;

    public Task(String title, Date startDate, Date endDate, Integer interval, boolean isActive) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getInterval() {
        return interval;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive){this.isActive = isActive;}

    @Override
    public String toString() {
        return "TaskDTO{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", interval=" + interval +
                ", isActive=" + isActive +
                '}';
    }
}