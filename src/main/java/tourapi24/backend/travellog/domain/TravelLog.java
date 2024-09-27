package tourapi24.backend.travellog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.place.domain.Place;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString //TODO DEBUG
@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private EmojiOpinion emojiOpinion;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private CongestionLevel congestionLevel;

    @ElementCollection(targetClass = SentenceOpinion.class)
    @CollectionTable(name = "travellog_sentence_opinions", joinColumns = @JoinColumn(name = "travellog_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<SentenceOpinion> sentenceOpinions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "travellog_visit_together", joinColumns = @JoinColumn(name = "travellog_id"))
    @Column(name = "place_id")
    @Builder.Default
    private List<Long> visitTogether = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "travellog_media", joinColumns = @JoinColumn(name = "travellog_id"))
    @Column(name = "media_url")
    @Builder.Default
    private List<String> media = new ArrayList<>();

    @NotNull
    private String content;

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long likeCount = 0L;

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
