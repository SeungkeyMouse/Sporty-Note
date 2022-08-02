package com.sportynote.server.service;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Record;
import com.sportynote.server.domain.Routine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.RecordRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.query.RecordDto;
import com.sportynote.server.repository.query.RoutineDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final MachineRepository machineRepository;
    private final UserBasicRepository userBasicRepository;

    /** 기록시 머신 하나의 한 세트 체크 CREATE */
    public boolean addRecord(RecordDto recordDto) {
        UserBasic userBasic = userBasicRepository.findById(recordDto.getUserId());
        Machine machine = machineRepository.findById(recordDto.getMachineIdx());

        Record record = Record.createRecord(userBasic,machine,recordDto.getSett(),recordDto.getKg(),
                recordDto.getCount(),recordDto.isComplete());

        recordRepository.save(record);
        return true;
    }
}
