package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.SchedulerCronRequestDTO;

import java.util.Collection;

public interface SchedulerService {

    Collection<String> previewSchedule(SchedulerCronRequestDTO schedulerCronRequestDTO);
}
