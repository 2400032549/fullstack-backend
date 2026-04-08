package com.mentor360.service;

import java.util.List;

import com.mentor360.dto.MentorshipMatchDTO;
import com.mentor360.dto.SessionResponseDTO;
import com.mentor360.dto.UserResponseDTO;
import com.mentor360.model.enums.MatchStatus;
import com.mentor360.model.enums.SessionStatus;

public interface AdminService {

    // USERS
    List<UserResponseDTO> getAllUsers();
    void deleteUser(Long userId);

    // MATCHES
    List<MentorshipMatchDTO> getAllMatches();
    void updateMatchStatus(Long matchId, MatchStatus status);

    // SESSIONS
    List<SessionResponseDTO> getAllSessions();
    void updateSessionStatus(Long sessionId, SessionStatus status);
}
