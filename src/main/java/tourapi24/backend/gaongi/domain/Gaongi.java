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

    public void increaseExp() {
        this.exp++;
    }

    public void calcLevel() {
        if ((this.exp >= 400) && (this.exp < 800)) {
            if (this.level != 2) {
                this.level = 2;
            }
        } else if (this.exp < 1300) {
            if (this.level != 3) {
                this.level = 3;
            }
        } else if (this.exp < 2500) {
            if (this.level != 4) {
                this.level = 4;
            }
        } else {
            if (this.level != 5) {
                this.level = 5;
            }
        }
    }
}
