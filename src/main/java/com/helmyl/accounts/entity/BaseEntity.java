package com.helmyl.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
