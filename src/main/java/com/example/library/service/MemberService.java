package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Value("${member.max-books:10}")
    private int maxBooks;

    public MemberService(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + id));
    }

    public void deleteMember(Long id) {
        Member member = getById(id);
        memberRepository.delete(member);
    }

    public Member borrowBook(Long memberId, Long bookId) {
        Member member = getById(memberId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + bookId));

        if (book.getAmount() <= 0) {
            throw new IllegalStateException("Book not available");
        }

        if (member.getBorrowedBooks().size() >= maxBooks) {
            throw new IllegalStateException("Member has reached borrow limit");
        }

        member.getBorrowedBooks().add(book);
        book.setAmount(book.getAmount() - 1);

        bookRepository.save(book);
        return memberRepository.save(member);
    }

    public Member returnBook(Long memberId, Long bookId) {
        Member member = getById(memberId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + bookId));

        if (!member.getBorrowedBooks().contains(book)) {
            throw new IllegalStateException("Member does not have this book");
        }

        member.getBorrowedBooks().remove(book);
        book.setAmount(book.getAmount() + 1);

        bookRepository.save(book);
        return memberRepository.save(member);
    }

    public List<Book> getBorrowedBooksByName(String name) {
        return memberRepository.findByName(name)
                .map(member -> List.copyOf(member.getBorrowedBooks()))
                .orElseThrow(() -> new NoSuchElementException("Member not found with name: " + name));
    }
}
