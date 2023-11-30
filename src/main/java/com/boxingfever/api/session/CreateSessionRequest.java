package com.boxingfever.api.session;

import java.util.Date;

public record CreateSessionRequest (
        Date startHour,
        Date endHour,
        int capacity,
        Date sessionDate,
        String className
){}
