package com.sportynote.server.repository.query;


import com.sportynote.server.domain.UserBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RecordDto {

    private Long machineIdx;

    private Integer sett;

    private Integer kg;

    private Integer count;

    private boolean complete;
    public RecordDto(){

    }

}
