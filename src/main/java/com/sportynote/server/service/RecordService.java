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
import com.sportynote.server.repository.query.RoutineMachineDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public boolean addRecord(String userId, RecordDto recordDto) {
        UserBasic userBasic = userBasicRepository.findById(userId);
        Machine machine = machineRepository.findById(recordDto.getMachineIdx());

        Record record = Record.createRecord(userBasic,machine,recordDto.getSett(),recordDto.getKg(),
                recordDto.getCount(),recordDto.isComplete());

        recordRepository.save(record);
        return true;
    }

    /** 기록시 머신 하나의 한 세트 체크 DELETE */
    public boolean deleteRecord(String userId, RecordDto recordDto) {
        // UserBasic userBasic = userBasicRepository.findById(userId);
        // Machine machine = machineRepository.findById(recordDto.getMachineIdx());
        Record record = recordRepository.findByIdentityId(userId, recordDto);
        recordRepository.deleteRecord(record.getIdx());
        return true;
    }

    /** 내 운동기록 불러오기 (날짜만) */
    public List<LocalDate> findByCalendar(String userid){
        return recordRepository.findByCalendar(userid);
    }

    /** 내 운동기록 불러오기 (날짜하나에 저장한 기록) */
    public List<RecordDto> findByRecordDay(String userId,String recordDay){
        LocalDate localDate = stringToLocalDate(recordDay);
        List<Record> recordList = recordRepository.findByRecordDay(localDate,userId);
        List<RecordDto> RecordLists = new ArrayList<>();
        for(Record record : recordList) {
            RecordLists.add(new RecordDto(record.getMachine().getIdx(),record.getSett(),
                    record.getKg(),record.getCount(),record.isComplete()));
        }
        return RecordLists;
    }


    public List<List<RecordDto>> previousRecord(String userId, Long machineIdx){
        List<List<RecordDto>> recordDtoLists = new ArrayList<>();
        recordDtoLists.add(recordRepository.findByPreviousRecordMAX(userId, machineIdx));
        recordDtoLists.add(recordRepository.findByPreviousRecordCNT(userId, machineIdx));
        return recordDtoLists;
    }


    /** String을 시간객체로 */
    public LocalDate stringToLocalDate(String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(day,formatter);
    }
    /** 시간객체를 String으로 */
    public String localDateToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }
}
