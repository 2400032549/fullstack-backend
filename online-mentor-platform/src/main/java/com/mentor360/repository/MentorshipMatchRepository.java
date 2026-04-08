package com.mentor360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentor360.model.MentorshipMatch;
import com.mentor360.model.User;

public interface MentorshipMatchRepository extends JpaRepository<MentorshipMatch, Long>{

	List<MentorshipMatch> findByMentee(User mentee);
	List<MentorshipMatch> findByMentor(User mentor);
}
