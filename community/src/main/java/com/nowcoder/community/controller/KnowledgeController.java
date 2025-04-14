package com.nowcoder.community.controller;

import com.nowcoder.community.entity.KnowledgeItem;
import com.nowcoder.community.entity.ReviewSchedule;
import com.nowcoder.community.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    /**
            * 创建知识条目
     * POST /api/knowledge
     */
    @PostMapping
    public ResponseEntity<KnowledgeItem> createKnowledgeItem(@RequestBody KnowledgeItem item) {
        knowledgeService.createKnowledgeItem(item);
        return ResponseEntity.ok(item);
    }

    /**​
            * 完成复习并生成下次计划
     * PUT /api/knowledge/review/{scheduleId}
     */
    @PutMapping("/review/{scheduleId}")
    public ResponseEntity<?> completeReview(
            @PathVariable int scheduleId,
            @RequestParam(defaultValue = "80") int retentionScore) {
        knowledgeService.completeReview(scheduleId, retentionScore);
        return ResponseEntity.ok().build();
    }

    /**​
            * 获取待复习列表
     * GET /api/knowledge/review/pending?userId={userId}
     */
    @GetMapping("/review/pending")
    public ResponseEntity<List<ReviewSchedule>> getPendingReviews(
            @RequestParam int userId) {
        List<ReviewSchedule> reviews = knowledgeService.getPendingReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    /**​
            * 获取知识条目详情（含复习计划）
            * GET /api/knowledge/{itemId}/with-reviews
     */
    @GetMapping("/{itemId}/with-reviews")
    public ResponseEntity<KnowledgeItem> getKnowledgeWithReviews(
            @PathVariable int itemId) {
        KnowledgeItem item = knowledgeService.getKnowledgeWithReviews(itemId);
        return ResponseEntity.ok(item);
    }

    /**​
            * 更新知识条目内容
     * PUT /api/knowledge/{itemId}
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<KnowledgeItem> updateKnowledgeItem(
            @PathVariable int itemId,
            @RequestBody KnowledgeItem item) {
        item.setItemId(itemId); // 确保ID一致性
        knowledgeService.updateKnowledgeItem(item);
        return ResponseEntity.ok(item);
    }

    /**​
            * 删除知识条目（级联删除复习计划）
            * DELETE /api/knowledge/{itemId}
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteKnowledgeItem(@PathVariable int itemId) {
        knowledgeService.deleteKnowledgeItem(itemId);
        return ResponseEntity.noContent().build();
    }
}