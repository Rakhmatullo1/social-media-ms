package com.rakhmatullo.postsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(length = 1024)
    private String description;
    private Date createdAt;
    private UUID userId;
    private boolean isPrivate = false;
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "post")
    private Set<Content> contents = new HashSet<>();
    @OneToMany(mappedBy = "post")
    private Set<Favorite> favorites = new HashSet<>();
}
