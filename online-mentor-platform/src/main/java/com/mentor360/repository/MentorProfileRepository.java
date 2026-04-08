package com.mentor360.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentor360.model.MentorProfile;

public interface MentorProfileRepository extends JpaRepository<MentorProfile, Long> {

}
