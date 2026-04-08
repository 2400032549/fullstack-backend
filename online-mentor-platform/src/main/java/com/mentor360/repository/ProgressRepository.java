package com.mentor360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentor360.model.MentorshipMatch;
import com.mentor360.model.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
	
	List<Progress> findByMatch(MentorshipMatch match);
}
