package com.lab4.demo.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Document(collection = "Comment")
public class Comment{

    @Id
    private String id;
    @Field("comment")
    private String content;
    @Field("user_id")
    private Long userId;
    @Field("track_id")
    private Long trackId;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", trackId=" + trackId +
                '}';
    }
}
