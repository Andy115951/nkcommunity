package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.KnowledgeItem;
import com.nowcoder.community.entity.ReviewSchedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
    // KnowledgeItem 操作
    int insertKnowledgeItem(KnowledgeItem item);
    int updateKnowledgeItem(KnowledgeItem item);
    KnowledgeItem selectKnowledgeItemById(int itemId);
    List<KnowledgeItem> selectKnowledgeItemsByUser(int userId, int offset, int limit);


    int deleteKnowledgeItem(int itemId);
}