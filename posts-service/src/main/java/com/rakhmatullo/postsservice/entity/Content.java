package com.rakhmatullo.postsservice.entity;

import com.rakhmatullo.postsservice.service.enums.MimeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Content {
    @Id
    @GeneratedValue
    private UUID id;
    private String url;
    private boolean isMain;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private MimeType mimeType;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
