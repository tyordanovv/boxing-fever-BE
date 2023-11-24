package com.boxingfever.service;

import com.boxingfever.api.session.CreateSessionRequest;
import com.boxingfever.api.session.SessionDto;

import java.util.List;
import java.util.Set;

public interface SessionService {
    List<SessionDto> getAllSessions();
    void deleteSession(Long id);
    String createSession(CreateSessionRequest request);
    void mapUserToSession(Long sessionId, Long userId);
}
