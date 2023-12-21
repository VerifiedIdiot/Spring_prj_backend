package com.Doggo.DoggoEx.controller;
import com.Doggo.DoggoEx.dto.MemberResDto;
import com.Doggo.DoggoEx.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    // 회원 조회
    @GetMapping("/members")
    public ResponseEntity<List<MemberResDto>> memberList() {
        List<MemberResDto> list = adminMemberService.getAdminMemberList();
        return ResponseEntity.ok(list);
    }

    // 회원 삭제
    @DeleteMapping("/members/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isTrue = adminMemberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }

    // 목록 페이징
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberResDto>> memberList( @RequestParam String grade,
                                                        @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        List<MemberResDto> list = adminMemberService.getMemberList(grade,page, size);
        return ResponseEntity.ok(list);
    }

    // 페이지 수 조회
    @GetMapping("/count")
    public ResponseEntity<Integer> listMembers(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer pageCnt = adminMemberService.getMembers(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }
}
