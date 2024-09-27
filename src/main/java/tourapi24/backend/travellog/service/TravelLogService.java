package tourapi24.backend.travellog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.repository.PlaceRepository;
import tourapi24.backend.travellog.domain.TravelLog;
import tourapi24.backend.travellog.dto.TravelLogCreateRequest;
import tourapi24.backend.travellog.dto.TravelLogListResponse;
import tourapi24.backend.travellog.dto.TravelLogResponse;
import tourapi24.backend.travellog.repository.TravelLogRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelLogService {

    private final TravelLogRepository travelLogRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public Long createTravelLog(CurrentUserInfo userInfo, TravelLogCreateRequest request) {
        Member member = memberRepository.findById(userInfo.getUserId()).orElseThrow();
        Place place = placeRepository.findById(request.getPlaceId()).orElseThrow();

        TravelLog travelLog = buildTravelLog(member, place, request);
        return travelLogRepository.save(travelLog).getId();

    }

    public TravelLogListResponse getRecentTravelLogs() {
        List<TravelLogResponse> travelLogs = travelLogRepository.findRecentTravelLogs().stream()
                .map(this::convertToTravelLogResponse)
                .collect(Collectors.toList());

        return TravelLogListResponse.builder()
                .travelLogs(travelLogs)
                .build();
    }

    @Transactional
    public TravelLogResponse getTravelLog(Long travelLogId) {
        TravelLog travelLog = travelLogRepository.findById(travelLogId).orElseThrow();
        travelLog.increaseViewCount();

        increaseExpByViewCount(travelLog);

        return convertToTravelLogResponse(travelLog);
    }

    public TravelLogListResponse getTravelLogsByQuery(String query) {
        List<TravelLogResponse> travelLogs = travelLogRepository.findTravelLogsByQuery(query).stream()
                .map(this::convertToTravelLogResponse)
                .collect(Collectors.toList());

        return TravelLogListResponse.builder()
                .travelLogs(travelLogs)
                .build();
    }

    private TravelLog buildTravelLog(Member member, Place place, TravelLogCreateRequest request) {
        return TravelLog.builder()
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
    }


    private TravelLogResponse convertToTravelLogResponse(TravelLog travelLog) {
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

    private void increaseExpByViewCount(TravelLog travelLog) {
        int addExp = 0;

        if (travelLog.getViewCount() > 1000) {
            addExp = 40;
        } else if (travelLog.getViewCount() > 500) {
            addExp = 25;
        } else if (travelLog.getViewCount() > 300) {
            addExp = 15;
        } else if (travelLog.getViewCount() > 200) {
            addExp = 10;
        } else if (travelLog.getViewCount() > 100) {
            addExp = 5;
        }

        Member member = travelLog.getMember();
        Gaongi gaongi = member.getGaongi();
        gaongi.increaseExp(addExp);
    }
}
