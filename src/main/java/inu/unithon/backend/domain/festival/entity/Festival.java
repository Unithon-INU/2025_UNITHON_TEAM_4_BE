package inu.unithon.backend.domain.festival.entity;

import jakarta.persistence.*;
import inu.unithon.backend.global.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 1000)
    private String imageUrl;
    @Column(length = 1000)
    private String address;
    private Long contentId;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public Festival(String title, String imageUrl, String address, Long contentId,
                    String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.address = address;
        this.contentId = contentId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
