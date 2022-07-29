package com.sportynote.server.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
@Where(clause = "deleted=false")
//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    private boolean deleted = Boolean.FALSE;

//    @PrePersist
//    public void onPrePersist(){
//        this.createdAt = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:")));
//        this.updatedAt = this.createdAt;
//    }

}