package com.mentor360.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentor360.model.MenteeProfile;

public interface MenteeProfileRepository extends JpaRepository<MenteeProfile, Long> {

}
