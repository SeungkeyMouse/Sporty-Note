package com.sportynote.server;

import com.sportynote.server.domain.Gym;
import com.sportynote.server.repository.GymRepository;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.dto.GymDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Initializer implements CommandLineRunner {
    //ServerApplication.class를 실행할때 매번 같이 실행되는 클래스입니다.
    //생성목적 : 예시 db를 실행시마다 넣어주는 용도이므로 매 실행시마다 Table을 create로 설정해주셔야 데이터 중복이 안됩니다.
    private final MachineRepository machineRepository;
    private final GymRepository gymRepository;

    @Override
    public void run(String... args) throws Exception {
        //하단 메소드 안의 내용들을 주석처리하면 실행시 데이터가 주입되지 않습니다.
        gymRepository.save(new GymDto(1,"바디스페이스", "37", "128","서울시", "강남구", "선릉로"));
        gymRepository.save(new GymDto(2,"정원헬스장", "36", "127","서울시", "성북구", "안암로"));
        gymRepository.save(new GymDto(3,"의정부헬스장", "37.5", "128","의정부시", "무슨구", "땡땡동"));

    }
}
