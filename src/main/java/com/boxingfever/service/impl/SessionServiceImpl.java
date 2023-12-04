package com.boxingfever.service.impl;

import com.boxingfever.api.session.CreateSessionRequest;
import com.boxingfever.api.session.SessionDto;
import com.boxingfever.api.session.UpdateSessionRequest;
import com.boxingfever.entity.Session;
import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.entity.User;
import com.boxingfever.exception.APIException;
import com.boxingfever.repository.SessionRepository;
import com.boxingfever.service.ClassService;
import com.boxingfever.service.SessionService;
import com.boxingfever.service.TrainerService;
import com.boxingfever.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final TrainerService trainerService;
    private final UserService userService;
    private final ClassService classService;

    @Autowired
    public SessionServiceImpl(
            SessionRepository sessionRepository,
            TrainerService trainerService,
            UserService userService,
            ClassService classService
    ) {
        this.sessionRepository = sessionRepository;
        this.trainerService = trainerService;
        this.userService = userService;
        this.classService = classService;
    }
    @Override
    public List<SessionDto> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();

        return sessions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public void createSession(CreateSessionRequest request) {
        Session session = new Session();
        session.setFromDate(request.startHour());
        session.setToDate(request.endHour());
        session.setCapacity(request.capacity());
        session.setSessionDate(request.sessionDate());

        TrainingClass trainingClass = classService.getClassByName(request.className());

        session.setAClass(trainingClass);

        sessionRepository.save(session);
    }

    @Override
    public void mapUserToSession(Long sessionId, Long userId) {
        User user = userService.getUser(userId);
        Session session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Session with id =  " + sessionId + " is not found!"));

        session.addUser(user);
        sessionRepository.save(session);
    }

    @Override
    public void updateSession(UpdateSessionRequest request) {
        Session session = sessionRepository.findById(request.id())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Session " + request.id() + " is not found!"));

        if (!Objects.equals(request.capacity(), 0)) {
            session.setCapacity(request.capacity());
        }
        sessionRepository.save(session);

    }
    private SessionDto convertToDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getFromDate(),
                session.getToDate(),
                session.getCapacity(),
                session.getSessionDate(),
                session.getUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toList()),
                session.getTrainers().stream()
                        .map(Trainer::convertTrainerToDto)
                        .collect(Collectors.toList()),
                session.getAClass().getClassName()
        );
    }
}
