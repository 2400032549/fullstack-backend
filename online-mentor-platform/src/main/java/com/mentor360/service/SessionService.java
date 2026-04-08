package com.mentor360.service;

import java.time.LocalDateTime;

import com.mentor360.dto.SessionRequestDTO;
import com.mentor360.dto.SessionResponseDTO;
import com.mentor360.model.Session;
import com.mentor360.model.enums.SessionStatus;

import java.util.List;

public interface SessionService {

	String bookSession(String email, SessionRequestDTO request);
    List<SessionResponseDTO> getMySessions(String email);

    List<SessionResponseDTO> getMentorSessions(String email);

    public void updateSessionStatus(Long sessionId, SessionStatus status);
    
    public void addMeetingLink(Long sessionId, String link);
}
