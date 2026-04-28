package com.mentor360.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mentor360.dto.SessionRequestDTO;
import com.mentor360.dto.SessionResponseDTO;
import com.mentor360.model.enums.SessionStatus;
import com.mentor360.service.SessionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    // ✅ MENTEE BOOKS SESSION (NO meeting link)
    @PreAuthorize("hasRole('MENTEE')")
    @PostMapping("/book")
    public ResponseEntity<String> bookSession(
            @RequestBody SessionRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                sessionService.bookSession(email, request)
        );
    }

    // ✅ MENTEE - VIEW OWN SESSIONS
    @GetMapping("/my")
    @PreAuthorize("hasRole('MENTEE')")
    public ResponseEntity<List<SessionResponseDTO>> getMySessions(Authentication auth) {

        return ResponseEntity.ok(
                sessionService.getMySessions(auth.getName())
        );
    }

    // ✅ MENTOR - VIEW SESSIONS
    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<SessionResponseDTO>> getMentorSessions(Authentication auth) {

        return ResponseEntity.ok(
                sessionService.getMentorSessions(auth.getName())
        );
    }

    // ✅ MENTOR - APPROVE / REJECT
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('MENTOR','ADMIN')")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam SessionStatus status) {

        sessionService.updateSessionStatus(id, status);
        return ResponseEntity.ok("Session status updated");
    }

    // 🔥 NEW API — MENTOR ADDS MEETING LINK
    @PutMapping("/{id}/link")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<String> addMeetingLink(
            @PathVariable Long id,
            @RequestParam String meetingLink) {

        sessionService.addMeetingLink(id, meetingLink);
        return ResponseEntity.ok("Meeting link added");
    }
}
