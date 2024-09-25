package tourapi24.backend.gaongi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.dto.GaongiResponse;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GaongiService {

    private final GaongiRepository gaongiRepository;

    @Transactional
    public void create(Member member) {
        gaongiRepository.save(Gaongi.builder()
                .member(member)
                .build());
    }

    @Transactional
    public GaongiResponse increaseExp(Long memberId) {
        Gaongi gaongi = gaongiRepository.findByMemberId(memberId);
        gaongi.increaseExp();
        gaongi.calcLevel();
        return GaongiResponse.builder()
                .id(gaongi.getId())
                .level(gaongi.getLevel())
                .exp(gaongi.getExp())
                .build();
    }

    public GaongiResponse getGaongi(Long memberId) {
        Gaongi gaongi = gaongiRepository.findByMemberId(memberId);
        return GaongiResponse.builder()
                .id(gaongi.getId())
                .level(gaongi.getLevel())
                .exp(gaongi.getExp())
                .build();
    }
}
