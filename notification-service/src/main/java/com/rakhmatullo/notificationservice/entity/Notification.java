package com.rakhmatullo.notificationservice.entity;

import com.rakhmatullo.notificationservice.entity.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("notification")
public class Notification {
    @Id
    @Column("id")
    private Long id;
    @Column("type")
    private NotificationType type;
    @Column("created_at")
    private String createdAt;
    @Column("user_id")
    private String userId;
    @Column("post_id")
    private String postId;
    @Column("to_user_id")
    private String toUserId;
    @Column("is_read")
    private boolean isRead = false;
}
