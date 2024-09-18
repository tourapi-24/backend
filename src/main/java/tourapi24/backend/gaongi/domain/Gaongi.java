package tourapi24.backend.gaongi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tourapi24.backend.member.domain.Member;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gaongi {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Builder.Default
    private Integer level = 1;

    @NotNull
    @Builder.Default
    private Integer exp = 0;
}
