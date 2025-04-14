package com.nowcoder.community.entity;

import java.time.LocalDateTime;

public class ReviewSchedule {
    private int scheduleId;
    private int itemId;
    private int userId;
    private LocalDateTime nextReviewTime;
    private int reviewCycle;
    private boolean isCompleted;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getNextReviewTime() {
        return nextReviewTime;
    }

    public void setNextReviewTime(LocalDateTime nextReviewTime) {
        this.nextReviewTime = nextReviewTime;
    }

    public int getReviewCycle() {
        return reviewCycle;
    }

    public void setReviewCycle(int reviewCycle) {
        this.reviewCycle = reviewCycle;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
