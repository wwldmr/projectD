package com.projectD.multisimbox.service;

import com.projectD.multisimbox.dto.SimCardRequest;
import com.projectD.multisimbox.dto.SimCardResponse;
import com.projectD.multisimbox.repository.SimCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimCardService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final SimCardRepository simCardRepository;

    public List<SimCardResponse> getAllSimCards() {
        return simCardRepository.findAllForGridRows().stream()
                .map(this::toGridResponse)
                .toList();
    }

    public SimCardResponse getSimCardById(long id) {
        return simCardRepository.findDescriptionRow(id).stream()
                .findFirst()
                .map(this::toDescriptionResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SIM card with id " + id + " not found"));
    }

    public SimCardResponse createSimCard(SimCardRequest request) {
        throw new UnsupportedOperationException("SimCard creation is not implemented for the new schema.");
    }

    private SimCardResponse toGridResponse(Object[] row) {
        return new SimCardResponse(
                toLong(row[0]),
                toStringValue(row[1]),
                toStringValue(row[2]),
                toStringValue(row[3]),
                toStringValue(row[4]),
                toStringValue(row[5]),
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private SimCardResponse toDescriptionResponse(Object[] row) {
        return new SimCardResponse(
                toLong(row[0]),
                toStringValue(row[1]),
                toStringValue(row[2]),
                toStringValue(row[3]),
                toStringValue(row[4]),
                toStringValue(row[5]),
                formatDateTime(row[6]),
                formatDateTime(row[7]),
                toLongNullable(row[8]),
                toStringValue(row[9]),
                toStringValue(row[10]),
                toStringValue(row[11])
        );
    }

    private Long toLong(Object value) {
        if (value == null) {
            throw new IllegalStateException("Expected non-null id value.");
        }
        return ((Number) value).longValue();
    }

    private Long toLongNullable(Object value) {
        if (value == null) {
            return null;
        }
        return ((Number) value).longValue();
    }

    private String toStringValue(Object value) {
        return value == null ? null : value.toString();
    }

    private String formatDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.format(DATE_TIME_FORMATTER);
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().format(DATE_TIME_FORMATTER);
        }
        return value.toString();
    }
}
