package tourapi24.backend.relationship.memberlikeplace;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.place.domain.Place;

@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class MemberLikePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
