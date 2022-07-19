package com.sportynote.server.repository;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Note;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class NoteRepository {
    private final EntityManager em;

    public void save(Note note) {
        em.persist(note);
    }

    /**
     *     내가 만든 노트가 있는 기구들만 호출
     */
    public List<MachineDto> findNotedMachineByUserId(String userId){
        List<Note> noteList = em.createQuery("select n from Note n"
                                + " join fetch n.machine"
                                + " join fetch n.userBasic where n.userBasic.userId=: userId"
                        , Note.class)
                .setParameter("userId", userId)
                .getResultList();

//      다 필요한데 해당 유저를 찾아야하면 아래와 같이 필터링
       /*
       noteList.stream().filter(note::isUserId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        */

        List<MachineDto> machineDtoList = new ArrayList<>();
        for (Note note : noteList) {
            Machine machine = note.getMachine();
            MachineDto machineDto = new MachineDto(machine.getIdx(),machine.getMachineName(),
                    machine.getTargetArea(), machine.getUrl());
            machineDtoList.add(machineDto);
        }

        return machineDtoList;
    }
}
