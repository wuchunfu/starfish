package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.ScheduleCronRequestDTO;

import java.util.Collection;

public interface SchedulerService {

    Collection<String> previewSchedule(ScheduleCronRequestDTO scheduleCronRequestDTO);
}
