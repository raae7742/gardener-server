package jandi.server.model;

/* 생성 일자, 수정 일자를 담은 추상 클래스 */

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime modified_at;
}
