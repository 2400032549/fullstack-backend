package com.mentor360.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mentor360.dto.SessionRequestDTO;
import com.mentor360.dto.SessionResponseDTO;
import com.mentor360.exception.InvalidOperationException;
import com.mentor360.exception.ResourceNotFoundException;
import com.mentor360.model.MentorshipMatch;
import com.mentor360.model.Session;
import com.mentor360.model.User;
import com.mentor360.model.enums.MatchStatus;
import com.mentor360.model.enums.SessionStatus;
import com.mentor360.repository.MentorshipMatchRepository;
import com.mentor360.repository.SessionRepository;
import com.mentor360.repository.UserRepository;
import com.mentor360.service.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final MentorshipMatchRepository matchRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    // ✅ MENTEE BOOKS SESSION
    @Override
    public String bookSession(String email, SessionRequestDTO request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MentorshipMatch match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        // ✅ Match must be active
        if (match.getStatus() != MatchStatus.ACTIVE) {
            throw new InvalidOperationException("Match is not active");
        }

        // ✅ Only mentee can book
        if (!match.getMentee().getId().equals(user.getId())) {
            throw new InvalidOperationException("You are not authorized for this match");
        }

        // ✅ Validate time
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new InvalidOperationException("Invalid time range");
        }

        // ✅ Create session WITHOUT meeting link
        Session session = new Session();
        session.setMatch(match);
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setStatus(SessionStatus.PENDING);
        session.setMeetingLink(null); // 🔥 IMPORTANT

        sessionRepository.save(session);

        return "Session booked successfully";
    }

    // ✅ MENTEE VIEW
    @Override
    public List<SessionResponseDTO> getMySessions(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return sessionRepository.findByMatch_Mentee(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ MENTOR VIEW
    @Override
    public List<SessionResponseDTO> getMentorSessions(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return sessionRepository.findByMatch_Mentor(user)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ MENTOR / ADMIN UPDATE STATUS
    @Override
    public void updateSessionStatus(Long sessionId, SessionStatus status) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        session.setStatus(status);
        sessionRepository.save(session);
    }

    // 🔥 NEW — MENTOR ADDS MEETING LINK
    @Override
    public void addMeetingLink(Long sessionId, String meetingLink) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        MentorshipMatch match = session.getMatch();

        // ✅ Only mentor of this match can add link
        if (match.getMentor() == null) {
            throw new InvalidOperationException("Invalid match mentor");
        }

        session.setMeetingLink(meetingLink);
        sessionRepository.save(session);
    }

    // ✅ COMMON MAPPER
    private SessionResponseDTO mapToDTO(Session s) {
        return new SessionResponseDTO(
                s.getId(),
                s.getMatch().getMentor().getName(),
                s.getMatch().getMentee().getName(),
                s.getStartTime(),
                s.getEndTime(),
                s.getMeetingLink(),
                s.getStatus()
        );
    }
}
