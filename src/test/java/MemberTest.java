import com.practice.boardproject.member.domain.MemberRepository;
import com.practice.boardproject.member.domain.MemberRequestDTO;
import com.practice.boardproject.member.domain.MemberResponseDTO;
import com.practice.boardproject.member.domain.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberTest {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberTest(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest() {
        MemberRequestDTO request = new MemberRequestDTO("user01", "pwd01");

        MemberResponseDTO response = memberService.signUp(request);

        Assertions.assertEquals("user01", response.getUsername());
    }

}
