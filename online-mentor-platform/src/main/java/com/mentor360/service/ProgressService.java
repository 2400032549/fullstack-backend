package com.mentor360.service;

import java.util.List;

import com.mentor360.dto.ProgressRequestDTO;
import com.mentor360.dto.ProgressResponseDTO;

public interface ProgressService {

    ProgressResponseDTO createProgress(String email, ProgressRequestDTO request);

    ProgressResponseDTO updateProgress(String email, Long progressId, ProgressRequestDTO request);

    List<ProgressResponseDTO> getProgressByMatch(String email, Long matchId);
}
