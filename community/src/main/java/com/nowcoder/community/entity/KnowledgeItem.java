package com.nowcoder.community.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeItem {
    private int itemId;
    private int userId;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private int difficulty = 1;
    // 新增关联字段
    private List<ReviewSchedule> reviewSchedules;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    // 添加getter和setter
    public List<ReviewSchedule> getReviewSchedules() {
        return reviewSchedules;
    }
    public void setReviewSchedules(List<ReviewSchedule> reviewSchedules) {
        this.reviewSchedules = reviewSchedules;
    }


}
