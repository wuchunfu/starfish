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

package org.metahut.starfish.server.exception;

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.exception.BusinessException;
import org.metahut.starfish.ingestion.collector.api.CollectorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.metahut.starfish.api.enums.Status.UNKNOWN_EXCEPTION;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResultEntity exceptionHandler(BusinessException exception) {
        String message = toI18nMessage(exception, exception.getMessage(), exception.getArgs());
        return ResultEntity.of(exception.getCode(), message);
    }

    @ExceptionHandler(value = { CollectorException.class })
    public ResultEntity exceptionHandler(RuntimeException exception) {
        String message = toI18nMessage(exception, exception.getMessage());
        return ResultEntity.of(UNKNOWN_EXCEPTION.getCode(), message);
    }

    /**
     * unknown exception handler
     * @param exception exception
     * @return result entity
     */
    @ExceptionHandler(value = Exception.class)
    public ResultEntity exceptionHandler(Exception exception) {
        String message = toI18nMessage(exception, UNKNOWN_EXCEPTION.getMessage(), exception.getMessage());
        return ResultEntity.of(UNKNOWN_EXCEPTION.getCode(), message);
    }

    private String toI18nMessage(Throwable exception, String message, Object... args) {
        try {
            message = messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
        }
        LOGGER.error(message, exception);
        return message;
    }
}
