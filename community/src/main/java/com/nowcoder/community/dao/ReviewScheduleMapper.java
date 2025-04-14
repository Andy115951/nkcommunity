package com.nowcoder.community.dao;

import com.nowcoder.community.entity.ReviewSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewScheduleMapper {
    // 插入复习计划
    int insertReviewSchedule(ReviewSchedule schedule);

    // 根据ID查询
    ReviewSchedule selectById(int scheduleId);

    // 查询用户的待复习计划
    List<ReviewSchedule> selectPendingReviews(int userId);

    // 更新复习状态
    int updateCompletionStatus(@Param("scheduleId") int scheduleId,
                               @Param("isCompleted") boolean isCompleted);

    List<ReviewSchedule> selectByItemId(@Param("itemId") int itemId);

    void deleteByItemId(int itemId);
}