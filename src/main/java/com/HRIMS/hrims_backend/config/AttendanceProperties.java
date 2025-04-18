package com.HRIMS.hrims_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "attendance")
@Data
public class AttendanceProperties {

    private String startTime;
    private int gracePeriodMinutes;
    private String latestCheckIn;
    private String earlyCheckoutTime;
    private String endTime;

}
