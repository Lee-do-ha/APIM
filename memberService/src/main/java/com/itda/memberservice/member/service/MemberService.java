package com.itda.memberservice.member.service;

import com.itda.memberservice.common.util.JwtUtil;
import com.itda.memberservice.member.dto.request.ChangePasswordRequest;
import com.itda.memberservice.member.dto.request.CreateMemberRequest;
import com.itda.memberservice.member.dto.request.LoginMemberRequest;
import com.itda.memberservice.member.dto.response.EmployeeSearchResponse;
import com.itda.memberservice.member.dto.response.MemberResponse;
import com.itda.memberservice.member.dto.response.NameSearchResponse;
import com.itda.memberservice.member.entity.Member;
import com.itda.memberservice.member.repository.MemberRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.List;
import java.util.Random;

import static jakarta.mail.Message.RecipientType.TO;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSender javaMailSender;
    @Value("${security.jwt.secret.key}")
    private String secretKey;
    private Random random;

    public Member register(CreateMemberRequest request) throws MessagingException {

        log.info("{MemberService} : register 실행");

        String randomPassword = createRandomPassword();

        Member save = memberRepository.save(Member.builder()
                .employeeId(request.getEmployeeId())
                .name(request.getName())
                .password(encoder.encode(randomPassword))
                .imageUrl(request.getImageUrl())
                .authority(request.getAuthority())
                .department(request.getDepartment())
                .position(request.getPosition())
                .email(request.getEmail())
                .build());

        sendEmailPassword(request.getEmail(), randomPassword);

        return save;

    }

    @Transactional(readOnly = true)
    public boolean employeeIdDuplicateCheck(String employeeId){

        return memberRepository.existsByEmployeeId(employeeId);

    }

    @Transactional(readOnly = true)
    public String login(LoginMemberRequest request){

        Member member = memberRepository.findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        byte[] secretKeyByte = DatatypeConverter.parseBase64Binary(secretKey);

        Key key = new SecretKeySpec(secretKeyByte, SignatureAlgorithm.HS256.getJcaName());

        if (encoder.matches(request.getPwd(), member.getPassword())) {
            Long accessExpiration = (long) (60 * 60 * 24 * 7 * 1000);
            return JwtUtil.createToken(request.getEmployeeId(), key, accessExpiration);
        } else {
            throw new RuntimeException("비밀번호가 일치하지않습니다.");
        }

    }

    public void delete(Long memberId) {

        log.info("{멤버 삭제} : memberId = " + memberId);

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원은 존재하지않습니다."));

        log.info("{멤버 삭제} : member = " + member);

        memberRepository.delete(member);

    }

    public void changePassword(ChangePasswordRequest request, String employeeId) {

        Member member = memberRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("회원이 존재하지 않습니다.")
                );

        if (!encoder.matches(request.getOriginalPassword(), member.getPassword())) {
            throw new RuntimeException("올바르지 않은 비밀번호입니다.");
        }

        member.changePassword(encoder.encode(request.getChangePassword()));

    }

    @Transactional(readOnly = true)
    public EmployeeSearchResponse findByEmployeeId(String id){

        return memberRepository.findMemberByEmployeeId(id);
    }

    @Transactional(readOnly = true)
    public List<NameSearchResponse> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponse> findAll(Pageable pageable) {

        return memberRepository.findMemberResponse(pageable);

    }

    @Transactional(readOnly = true)
    public MemberResponse myInformation(String employeeId){

        return memberRepository.findMemberResponseByEmployeeId(employeeId);

    }

    @Transactional(readOnly = true)
    public void sendEmailPassword(String email, String password) throws MessagingException {

        String title = "[ITDA] 임시 비밀번호 안내";

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(TO, email);
        message.setSubject(title);

        String emailMsg = "";
        emailMsg += "<div style='margin:20px; display: flex; flex-direction: column; justify-content: center; align-items: center;'> \n" +
//                "      <img src=\"https://findmyguide.s3.amazonaws.com/logo-nobg.png\" alt=\"ASAP Logo Img\" style=\"width:380px;\">\n" +
                "      <h1 style='font-weight: 900;'> ITDA 임시 비밀번호 발급 안내 </h1> \n" +
                "\n" +
                "      <br> \n" +
                "      <h4 style='font-weight: 600;'>로그인을 위한 임시 비밀번호입니다.</h4> \n" +
                "      <h4 style='font-weight: 600;'>아래 임시 비밀번호를 통해 로그인을 진행해주시기바랍니다.<h4> \n" +
                "      <br> \n" +
//                "        <h2 style='color:blue; font-weight: 800;'>회원가입 인증 코드입니다.</h2> \n" +
                "        <div style='font-size:50px'>\n" + password +"   \n" +
                "        </div>\n" +
                "     <br>  \n" +
                "     <h4 style='font-weight: 600;'>인증 코드 오류가 생긴 경우 고객센터로 문의바랍니다</h4> \n" +
                "     <button style=\"margin-top: 10px; background-color: white; border-radius: 4px; border: none;\"><a href=\"https://k9c201.p.ssafy.io/\" style=\"text-decoration: none; color: blue;\">홈페이지 바로가기</a></button>\n ";

        message.setText(emailMsg, "utf-8", "html");

        javaMailSender.send(message);

    }

    public String createRandomPassword() {
        StringBuilder password = new StringBuilder();

        for(int i = 0 ; i < 8 ; i++){

            int idx = random.nextInt(3);

            switch (idx) {
                case 0 -> password.append((char) (random.nextInt(26) + 97));
                case 1 -> password.append((char) (random.nextInt(26) + 65));
                case 2 -> password.append( (random.nextInt(9)));
                default -> password.append((char) random.nextInt() + 97);
            }

        }

        return password.toString();

    }
}
