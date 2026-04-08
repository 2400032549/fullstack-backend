package com.mentor360.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.mentor360.dto.MatchRequestDTO;
import com.mentor360.dto.MatchingResponseDTO;
import com.mentor360.dto.MentorshipMatchDTO;
import com.mentor360.service.MatchingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/matching")   // ✅ FIXED
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/mentors")
    public List<MatchingResponseDTO> getMentors() {
        return matchingService.findMentors(); // make sure this exists
    }

    @PostMapping("/create")   // ✅ FIXED PATH
    @PreAuthorize("hasRole('MENTEE')")
    public ResponseEntity<String> createMatch(@RequestBody MatchRequestDTO request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
//        System.out.println("Creating match for mentorId: " + request.getMentorId());

        matchingService.createMatch(request.getMentorId(), email);
        
        return  ResponseEntity.ok("Match created successfully for mentorId: " + request.getMentorId());
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('MENTEE','MENTOR')")
    public ResponseEntity<List<MentorshipMatchDTO>> getMyMatches() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ResponseEntity.ok(matchingService.getMyMatches(email));
    }
}
