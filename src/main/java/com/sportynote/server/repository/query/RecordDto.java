package com.sportynote.server.repository.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RecordDto {
    private String userId;
    private Long machineIdx;

    private Integer sett;

    private Integer kg;

    private Integer count;

    private boolean complete;

}
