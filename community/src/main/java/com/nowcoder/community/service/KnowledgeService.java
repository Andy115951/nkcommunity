package com.nowcoder.community.service;

import com.nowcoder.community.dao.KnowledgeMapper;
import com.nowcoder.community.dao.ReviewScheduleMapper;
import com.nowcoder.community.entity.KnowledgeItem;
import com.nowcoder.community.entity.ReviewSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private ReviewScheduleMapper reviewScheduleMapper;

    // 艾宾浩斯复习周期（分钟/小时/天）
    private static final int[] REVIEW_CYCLES = {
            5,      // 5分钟
            30,     // 30分钟
            720,    // 12小时
            1440,   // 1天
            2880,   // 2天
            5760,   // 4天
            10080,  // 7天
            21600   // 15天
    };

    /**
            * 创建知识条目并初始化复习计划
     */
    @Transactional
    public void createKnowledgeItem(KnowledgeItem item) {
        // 1. 保存知识条目
        knowledgeMapper.insertKnowledgeItem(item);

        // 2. 生成首次复习计划（使用标准setter）
        ReviewSchedule firstReview = new ReviewSchedule();
        firstReview.setItemId(item.getItemId());
        firstReview.setUserId(item.getUserId());
        firstReview.setNextReviewTime(LocalDateTime.now().plusMinutes(REVIEW_CYCLES[0]));
        firstReview.setReviewCycle(1);
        firstReview.setCompleted(false);

        reviewScheduleMapper.insertReviewSchedule(firstReview);
    }

    /**
            * 处理复习完成（更新状态并生成下次复习计划）
            */
    @Transactional
    public void completeReview(int scheduleId, int retentionScore) {
        // 1. 获取当前复习计划
        ReviewSchedule current = reviewScheduleMapper.selectById(scheduleId);
        if (current == null) {
            throw new IllegalArgumentException("未找到对应的复习计划");
        }

        // 2. 标记为已完成
        current.setCompleted(true);
        reviewScheduleMapper.updateCompletionStatus(current.getScheduleId(), true);

        // 3. 根据记忆保持分数调整下次复习周期
        int nextCycle = calculateNextCycle(current.getReviewCycle(), retentionScore);

        // 4. 只有未完成所有周期时才创建新计划
        if(nextCycle < REVIEW_CYCLES.length) {
            ReviewSchedule nextReview = new ReviewSchedule();
            nextReview.setItemId(current.getItemId());
            nextReview.setUserId(current.getUserId());
            nextReview.setNextReviewTime(calculateNextReviewTime(nextCycle));
            nextReview.setReviewCycle(nextCycle + 1); // 进入下一周期
            nextReview.setCompleted(false);

            reviewScheduleMapper.insertReviewSchedule(nextReview);
        }
    }

    /**
            * 获取用户待复习列表
     */
    public List<ReviewSchedule> getPendingReviews(int userId) {
        return reviewScheduleMapper.selectPendingReviews(userId);
    }

    /**
            * 获取知识条目及关联的复习计划
     */
    public KnowledgeItem getKnowledgeWithReviews(int itemId) {
        // 1. 查询知识条目
        KnowledgeItem item = knowledgeMapper.selectKnowledgeItemById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("未找到知识条目");
        }

        // 2. 查询关联的复习计划
        List<ReviewSchedule> reviews = reviewScheduleMapper.selectByItemId(itemId);

        // 3. 设置关联关系
        item.setReviewSchedules(reviews);

        return item;
    }

    // ========== 私有方法 ==========

    /**
            * 计算下次复习周期（根据记忆保持分数动态调整）
            * @param currentCycle 当前周期 (1-8)
     * @param retentionScore 记忆保持分数 (0-100)
     * @return 下次应该使用的周期索引
     */
    private int calculateNextCycle(int currentCycle, int retentionScore) {
        if(retentionScore >= 80) {
            // 记忆良好：加快进度（跳过一个周期）
            return Math.min(currentCycle + 2, REVIEW_CYCLES.length - 1);
        } else if(retentionScore >= 50) {
            // 记忆一般：按原计划
            return currentCycle + 1;
        } else {
            // 记忆较差：退回前一个周期
            return Math.max(currentCycle - 1, 0);
        }
    }

    /**
            * 计算下次复习时间
     */
    private LocalDateTime calculateNextReviewTime(int cycleIndex) {
        int minutes = REVIEW_CYCLES[cycleIndex];
        return LocalDateTime.now().plusMinutes(minutes);
    }

    @Transactional
    public void updateKnowledgeItem(KnowledgeItem item) {
        // 1. 参数校验
        if (item == null || item.getItemId() <= 0) {
            throw new IllegalArgumentException("知识条目ID无效");
        }

        // 2. 检查条目是否存在
        KnowledgeItem existing = knowledgeMapper.selectKnowledgeItemById(item.getItemId());
        if (existing == null) {
            throw new IllegalArgumentException("未找到ID为 " + item.getItemId() + " 的知识条目");
        }

        // 3. 保留原始创建时间（不允许修改）
        item.setCreatedAt(existing.getCreatedAt());

        // 4. 执行更新
        int affectedRows = knowledgeMapper.updateKnowledgeItem(item);
        if (affectedRows == 0) {
            throw new RuntimeException("更新知识条目失败");
        }
    }

    @Transactional
    public void deleteKnowledgeItem(int itemId) {
        // 1. 参数校验
        if (itemId <= 0) {
            throw new IllegalArgumentException("知识条目ID无效");
        }

        // 2. 级联删除关联的复习计划
        reviewScheduleMapper.deleteByItemId(itemId);

        // 3. 删除主条目
        int affectedRows = knowledgeMapper.deleteKnowledgeItem(itemId);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("删除失败，可能条目不存在");
        }
    }
}