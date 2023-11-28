package com.boxingfever.controller;

import com.boxingfever.api.session.CreateSessionRequest;
import com.boxingfever.api.session.SessionDto;
import com.boxingfever.api.trainer.CreateTrainerRequest;
import com.boxingfever.entity.Trainer;
import com.boxingfever.service.SessionService;
import com.boxingfever.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(
            SessionService sessionService
    ){
        this.sessionService = sessionService;
    }

    @GetMapping()
    public ResponseEntity<List<SessionDto>> getSessions(){
        List<SessionDto> sessions = sessionService.getAllSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @PostMapping("{sessionId}/{userId}")
    public ResponseEntity<?> mapToUser(@PathVariable Long sessionId, @PathVariable Long userId){
        sessionService.mapUserToSession(sessionId,userId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createSession(@RequestBody CreateSessionRequest request){
        String str = sessionService.createSession(request);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id){
        sessionService.deleteSession(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}