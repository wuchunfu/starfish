/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.SchedulerCronRequestDTO;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.server.service.SchedulerService;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final IScheduler scheduler;

    public SchedulerServiceImpl(IScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Collection<String> previewSchedule(SchedulerCronRequestDTO schedulerCronRequestDTO) {
        ScheduleCronParameter parameter = new ScheduleCronParameter();
        parameter.setCrontab(schedulerCronRequestDTO.getCron());
        parameter.setStartTime(schedulerCronRequestDTO.getStartTime());
        parameter.setEndTime(schedulerCronRequestDTO.getEndTime());
        parameter.setTimezoneId(schedulerCronRequestDTO.getTimezoneId());
        return scheduler.previewSchedule(parameter);
    }

}
