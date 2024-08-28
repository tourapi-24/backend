package tourapi24.backend.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String profileImage;
    private String bio;
    private Integer ageRange; // 10, 20, ... , 60
    private String birthday; // "0101" ~ "1231"
}
