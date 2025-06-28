package com.example.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.model.Book;
import com.example.library.model.Member;
import com.example.library.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        Member saved = memberService.createMember(member);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{memberId}/borrow/{bookId}")
    public ResponseEntity<Member> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return ResponseEntity.ok(memberService.borrowBook(memberId, bookId));
    }

    @PostMapping("/{memberId}/return/{bookId}")
    public ResponseEntity<Member> returnBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return ResponseEntity.ok(memberService.returnBook(memberId, bookId));
    }

    @GetMapping("/{name}/books")
    public List<Book> getBooksBorrowedByMember(@PathVariable String name) {
        return memberService.getBorrowedBooksByName(name);
    }
}
