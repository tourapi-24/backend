package tourapi24.backend.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public int updateUsername(Long id, String username) {
        return memberRepository.updateUsernameById(id, username);

    }

    @Transactional
    public int updateBio(Long id, String bio) {
        return memberRepository.updateUsernameById(id, bio);
    }
}
