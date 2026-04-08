package com.mentor360.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentor360.model.MentorshipMatch;
import com.mentor360.model.Session;
import com.mentor360.model.User;

public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByMatch_Mentee(User mentee);
	List<Session> findByMatch_Mentor(User mentor);
	List<Session> findByMatchAndStartTimeLessThanAndEndTimeGreaterThan(
	        MentorshipMatch match,
	        LocalDateTime endTime,
	        LocalDateTime startTime
	);
    List<Session> findByMatchId(Long matchId);
}
