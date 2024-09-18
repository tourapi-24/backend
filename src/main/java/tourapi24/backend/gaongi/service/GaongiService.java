package tourapi24.backend.gaongi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class GaongiService {

    private final GaongiRepository gaongiRepository;

    public void create(Member member) {
        gaongiRepository.save(Gaongi.builder()
                .member(member)
                .build());
    }
}
