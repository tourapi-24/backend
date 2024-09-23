package tourapi24.backend.travellog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.repository.PlaceRepository;
import tourapi24.backend.travellog.domain.TravelLog;
import tourapi24.backend.travellog.dto.TravelLogCreateRequest;
import tourapi24.backend.travellog.dto.TravelLogCreateResponse;
import tourapi24.backend.travellog.dto.TravelLogResponse;
import tourapi24.backend.travellog.repository.TravelLogRepository;

@Service
@RequiredArgsConstructor
public class TravelLogService {

    private final TravelLogRepository travelLogRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    public TravelLogCreateResponse createTravelLog(CurrentUserInfo userInfo, TravelLogCreateRequest request) {
        Member member = memberRepository.findById(userInfo.getUserId()).orElseThrow();
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow();

        TravelLog travelLog = TravelLog.builder()
                .member(member)
                .place(place)
                .title(request.getTitle())
                .date(request.getDate())
                .emojiOpinion(request.getEmojiOpinion())
                .congestionLevel(request.getCongestionLevel())
                .sentenceOpinions(request.getSentenceOpinion())
                .visitTogether(request.getVisitTogether())
                .media(request.getMedia())
                .content(request.getContent())
                .build();
        return TravelLogCreateResponse.builder()
                .travelLogId(travelLogRepository.save(travelLog).getId())
                .build();
    }

    public TravelLogResponse getTravelLog(Long travelLogId) {
        TravelLog travelLog = travelLogRepository.findById(travelLogId).orElseThrow();
        return TravelLogResponse.builder()
                .id(travelLog.getId())
                .memberId(travelLog.getMember().getId())
                .placeId(travelLog.getPlace().getId())
                .placeName(travelLog.getPlace().getTitle())
                .placeContentType(travelLog.getPlace().getContentType())
                .placeAddress(travelLog.getPlace().getAddress())
                .title(travelLog.getTitle())
                .date(travelLog.getDate())
                .emojiOpinion(travelLog.getEmojiOpinion().name())
                .congestionLevel(travelLog.getCongestionLevel())
                .sentenceOpinions(travelLog.getSentenceOpinions())
                .visitTogether(travelLog.getVisitTogether())
                .media(travelLog.getMedia())
                .content(travelLog.getContent())
                .build();
    }
}
