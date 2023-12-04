package com.boxingfever.service;

import com.boxingfever.api.session.CreateSessionRequest;
import com.boxingfever.api.session.SessionDto;
import com.boxingfever.api.session.UpdateSessionRequest;

import java.util.List;
import java.util.Set;

public interface SessionService {
    List<SessionDto> getAllSessions();
    void deleteSession(Long id);
    void createSession(CreateSessionRequest request);
    void mapUserToSession(Long sessionId, Long userId);
    void updateSession(UpdateSessionRequest request);
}
