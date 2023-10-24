package com.itda.memberservice.member.service;

import com.itda.memberservice.member.entity.dto.request.CreateMemberRequest;
import com.itda.memberservice.member.entity.dto.response.MemberResponse;
import com.itda.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public MemberResponse createMember(CreateMemberRequest createMemberRequest) {

        return new MemberResponse();
    }
}