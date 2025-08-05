package com.streaming.service;

import com.streaming.dto.EnrichedData;
import com.streaming.dto.Movement;
import com.streaming.mapper.StatusMapper;
import com.streaming.producer.EnrichedDataProducer;
import com.streaming.repository.PersonRepository;
import com.streaming.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataEnrichmentService {

    private final RequestRepository requestRepository;
    private final PersonRepository personRepository;
    private final StatusMapper statusMapper;
    private final EnrichedDataProducer enrichedDataProducer;

    public DataEnrichmentService(RequestRepository requestRepository,
                                 PersonRepository personRepository,
                                 StatusMapper statusMapper,
                                 EnrichedDataProducer enrichedDataProducer) {
        this.requestRepository = requestRepository;
        this.personRepository = personRepository;
        this.statusMapper = statusMapper;
        this.enrichedDataProducer = enrichedDataProducer;
    }

    public void processMovement(Movement movement) {
        Long requestId = movement.getRequestId();
        log.info("Processing event for request ID: {}", requestId);

        // The repository is responsible for verifying if this is the latest movement
        requestRepository.findLatestStatus(requestId).ifPresent(latestStatus -> {
            if (!movement.getStatus().equals(latestStatus)) {
                log.warn("Event for request {} with status {} is not the latest ({}). Ignoring.",
                        requestId, movement.getStatus(), latestStatus);
                return;
            }

            handleStatus(requestId, latestStatus);
        });
    }

    private void handleStatus(Long requestId, String status) {
        if ("PD".equals(status) || "IP".equals(status)) {
            enrichAndSend(requestId, status);
        } else if ("CP".equals(status) || "CN".equals(status)) {
            enrichedDataProducer.sendTombstone(requestId);
        }
    }

    private void enrichAndSend(Long requestId, String status) {
        requestRepository.findPersonIdByRequestId(requestId).ifPresent(personId -> {
            personRepository.findPersonNameById(personId).ifPresent(personName -> {
                String statusDescription = statusMapper.map(status);

                EnrichedData finalData = new EnrichedData(requestId, personId, personName, statusDescription);
                enrichedDataProducer.sendEnrichedData(finalData);
            });
        });
    }
}