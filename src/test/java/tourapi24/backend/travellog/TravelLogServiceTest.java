package tourapi24.backend.travellog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.domain.BusanGu;
import tourapi24.backend.place.domain.GovContentType;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.repository.PlaceRepository;
import tourapi24.backend.travellog.domain.CongestionLevel;
import tourapi24.backend.travellog.domain.EmojiOpinion;
import tourapi24.backend.travellog.domain.SentenceOpinion;
import tourapi24.backend.travellog.dto.TravelLogCreateRequest;
import tourapi24.backend.travellog.repository.TravelLogRepository;
import tourapi24.backend.travellog.service.TravelLogService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TravelLogServiceTest {

    @Autowired
    private TravelLogService travelLogService;


    @Autowired
    private TravelLogRepository travelLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void createTravelLog() {
        // given
        Member member = Member.builder()
                .socialId("12345")
                .email("test@test.com")
                .username("user_1")
                .provider(Provider.KAKAO)
                .gender(Gender.MALE)
                .ageRange(AgeRange.TWENTY)
                .birthday("0101")
                .isLocal(false)
                .build();
        memberRepository.save(member);

        Place place1 = Place.builder()
                .contentId(12345L)
                .kakaoId(12345L)
                .contentType(GovContentType.관광지)
                .title("관광지_1")
                .tel("010-1234-5678")
                .address("address")
                .busanGu(BusanGu.강서구)
                .isCongestion(false)
                .build();
        placeRepository.save(place1);
        System.out.println(place1.getId());

        Place place2 = Place.builder()
                .contentId(12345L)
                .kakaoId(12345L)
                .contentType(GovContentType.문화시설)
                .title("문화시설_2")
                .tel("010-1234-5678")
                .address("address")
                .busanGu(BusanGu.강서구)
                .isCongestion(false)
                .build();
        placeRepository.save(place2);
        System.out.println(place2.getId());

        memberRepository.flush();
        placeRepository.flush();

        TravelLogCreateRequest request = TravelLogCreateRequest.builder()
                .placeId(1L)
                .title("사색에 잠겨 겯는 여행 혼자라도 괜찮아")
                .date(LocalDateTime.parse("2021-10-10T10:10:10"))
                .emojiOpinion(EmojiOpinion.HAPPY)
                .congestionLevel(CongestionLevel.LITTLE)
                .sentenceOpinion(Arrays.asList(SentenceOpinion.BEAUTIFUL_LIGHTING, SentenceOpinion.GOOD_FOR_DRIVE))
                .visitTogether(List.of(2L))
                .media(Arrays.asList("https://media1.com", "https://media2.com"))
                .content("content")
                .build();

        CurrentUserInfo userInfo = CurrentUserInfo.builder()
                .userId(1L)
                .username("user_1")
                .build();

        // when
        travelLogService.createTravelLog(userInfo, request);
        // then
        System.out.println(travelLogRepository.findAll().getFirst());
        assertEquals("사색에 잠겨 겯는 여행 혼자라도 괜찮아", travelLogRepository.findAll().getFirst().getTitle());

    }
}
