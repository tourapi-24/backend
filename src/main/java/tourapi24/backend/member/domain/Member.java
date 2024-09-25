package tourapi24.backend.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.relationship.memberlikeplace.MemberLikePlace;
import tourapi24.backend.travellog.domain.TravelLog;

import java.util.List;

@Entity
@EntityListeners(StringTrimmer.class)
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id; // 서비스 고유 ID

    @NotNull
    private String socialId; // 네이버/카카오에서 받아온 ID
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @NotNull
    private Boolean isLocal;

    private String profileImage;

    private String bio;

    @OneToOne(mappedBy = "member")
    private Gaongi gaongi;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<TravelLog> travelLogs;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberLikePlace> likePlaces;
}
