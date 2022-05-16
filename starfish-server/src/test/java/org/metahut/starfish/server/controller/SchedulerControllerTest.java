package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.SchedulerController;
import org.metahut.starfish.api.dto.ScheduleCronRequestDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.Date;

@SpringBootTest
@Disabled
public class SchedulerControllerTest {
    @Autowired
    private SchedulerController schedulerController;

    @Test
    public void validatorTest() throws Exception {
        ScheduleCronRequestDTO scheduleCronRequestDTO = new ScheduleCronRequestDTO();
        scheduleCronRequestDTO.setEndTime(new Date());
        scheduleCronRequestDTO.setStartTime(new Date());
        scheduleCronRequestDTO.setTimezoneId("1");
        ConstraintViolationException exception = Assertions.assertThrows(ConstraintViolationException.class, () -> schedulerController.previewSchedule(scheduleCronRequestDTO));
        Assertions
                .assertEquals("previewSchedule.arg0.cron: {parameter.not.null}", exception.getMessage());
    }


}
