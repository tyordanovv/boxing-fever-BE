package com.boxingfever.api.session;

import java.util.Date;

public record UpdateSessionRequest(
        long id,
        Date startHour,
        Date endHour,
        int capacity,
        Date sessionDate,
        String className
){}
