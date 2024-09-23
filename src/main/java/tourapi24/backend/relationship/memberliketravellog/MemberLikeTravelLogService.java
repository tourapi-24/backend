package tourapi24.backend.relationship.memberliketravellog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.travellog.domain.TravelLog;
import tourapi24.backend.travellog.repository.TravelLogRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLikeTravelLogService {

    private final MemberRepository memberRepository;
    private final TravelLogRepository travelLogRepository;
    private final MemberLikeTravelLogRepository memberLikeTravelLogRepository;

    @Transactional
    public void likeTravelLog(Long memberId, Long travelLogId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        TravelLog travelLog = travelLogRepository.findById(travelLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 여행기를 찾을 수 없습니다."));

        if (memberLikeTravelLogRepository.existsByMemberAndTravelLog(member, travelLog)) {
            throw new IllegalStateException("이미 좋아요를 누른 여행기입니다.");
        }

        MemberLikeTravelLog memberLikeTravelLog = MemberLikeTravelLog.builder()
                .member(member)
                .travelLog(travelLog)
                .build();
        memberLikeTravelLogRepository.save(memberLikeTravelLog);

        travelLog.updateLikeCount(memberLikeTravelLogRepository.countByTravelLog(travelLog));
        travelLogRepository.save(travelLog);
    }

    @Transactional
    public void unlikeTravelLog(Long memberId, Long travelLogId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        TravelLog travelLog = travelLogRepository.findById(travelLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 여행기를 찾을 수 없습니다."));

        memberLikeTravelLogRepository.deleteByMemberAndTravelLog(member, travelLog);

        travelLog.updateLikeCount(memberLikeTravelLogRepository.countByTravelLog(travelLog));
        travelLogRepository.save(travelLog);
    }

    public List<TravelLog> getLikedTravelLogs(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        return memberLikeTravelLogRepository.findAllByMember(member).stream()
                .map(MemberLikeTravelLog::getTravelLog)
                .toList();
    }

}
