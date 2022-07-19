package com.sportynote.server.dto;


import com.sportynote.server.domain.Machine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class Routinedto {
    private String userid;
    private String routineName;
    private List<Integer> machines;
}