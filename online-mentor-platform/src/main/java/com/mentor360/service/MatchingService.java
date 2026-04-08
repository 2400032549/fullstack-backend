package com.mentor360.service;

import java.util.List;
import com.mentor360.dto.MatchingResponseDTO;
import com.mentor360.dto.MentorshipMatchDTO;

public interface MatchingService {

    List<MatchingResponseDTO> findMentors();  // ✅ REQUIRED

    void createMatch(Long mentorId, String menteeEmail);
    List<MentorshipMatchDTO> getMyMatches(String email);
}
