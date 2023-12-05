//package com.Doggo.DoggoEx.service;
//
//import com.Doggo.DoggoEx.dto.MemberReqDto;
//import com.Doggo.DoggoEx.dto.MemberResDto;
//import com.Doggo.DoggoEx.dto.TokenDto;
//import com.Doggo.DoggoEx.entity.Member;
//import com.Doggo.DoggoEx.jwt.TokenProvider;
//import com.Doggo.DoggoEx.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AuthService {
//    private final AuthenticationManagerBuilder managerBuilder; // 인증을 담당하는 클래스
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final TokenProvider tokenProvider;
//
//    public MemberResDto signup(MemberReqDto requestDto) {
//        if (memberRepository.existsByMemberEmail(requestDto.getMemberEmail())) {
//            throw new RuntimeException("이미 가입되어 있는 유저입니다");
//        }
//        Member member = requestDto.toEntity(passwordEncoder);
//        return MemberResDto.of(memberRepository.save(member));
//    }
//    public TokenDto login(MemberReqDto requestDto) {
//        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
//        log.info("authenticationToken: {}", authenticationToken);
//
//        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
//        log.info("authentication: {}", authentication);
//
//        return tokenProvider.generateTokenDto(authentication);
//    }
//
//    // accessToken 재발급
//    public String createAccessToken(String refreshToken) {
//        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
//        return tokenProvider.generateAccessToken(authentication);
//    }
//}
