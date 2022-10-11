package com.sportynote.server.repository.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralNotesDto {
    private List<Long> machineIdxs = new ArrayList<>();

}

