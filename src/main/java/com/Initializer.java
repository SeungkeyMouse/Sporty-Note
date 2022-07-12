package com;

import com.sportynote.server.Enum.SocialType;
import com.sportynote.server.domain.Gym;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import com.sportynote.server.repository.GymRepository;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.GymDto;
import com.sportynote.server.service.GymService;
import com.sportynote.server.service.MachineService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class Initializer implements CommandLineRunner {
    //ServerApplication.class를 실행할때 매번 같이 실행되는 클래스입니다.
    //생성목적 : 예시 db를 실행시마다 넣어주는 용도이므로 매 실행시마다 Table을 create로 설정해주셔야 데이터 중복이 안됩니다.
    private final MachineRepository machineRepository;
    private final UserBasicRepository userBasicRepository;
    private final GymRepository gymRepository;
    private final GymService gymService;
    private final MachineService machineService;
    private final UserFavoriteRepository favoriteRepository;
    @Override
    public void run(String... args) throws Exception {
        //하단 메소드 안의 내용들을 주석처리하면 실행시 데이터가 주입되지 않습니다.
        System.out.println("Initializer.java : CommandLineRunner 실행");
        gymService.save(new GymDto("바디스페이스", "37", "128","서울시", "강남구", "선릉로"));
        gymService.save(new GymDto("정원헬스장", "36", "127","서울시", "성북구", "안암로"));
        gymService.save(new GymDto("의정부헬스장", "37.5", "128","의정부시", "무슨구", "땡땡동"));

        userBasicSetup();
        machineSetup();

        printUserFavorite();
    }

    void printUserFavorite() {
        List<UserFavorite> all = favoriteRepository.findAll();
        for (UserFavorite userFavorite : all) {
            System.out.println("-------A");
            List<Machine> userFavoriteMachines = userFavorite.getUserFavoriteMachines();
            for (Machine userFavoriteMachine : userFavoriteMachines) {
                System.out.println("userFavoriteMachine = " + userFavoriteMachine.getMachineName());
            }
        }
    }

    void machineSetup() {
        Machine machine = new Machine();
        machine.setMachineName("벤치프레스");
        machine.setTargetArea("가슴");
        machine.setUrl("https://www.naver.com");
        machine.setGym(gymRepository.findAll().get(0));

        machineRepository.save(machine);

        Machine machine2 = new Machine();
        machine2.setMachineName("랫풀다운");
        machine2.setTargetArea("등");
        machine2.setUrl("https://www.daum.net");
        machine2.setGym(gymRepository.findAll().get(1));

        machineRepository.save(machine2);

        machineService.addFavorite("123123", 7);
        machineService.addFavorite("123123", 6);

        machineService.addFavorite("777777", 7);
    }

    void userBasicSetup() {
        UserBasic userBasic = new UserBasic();
        userBasic.setUserId("123123");
        userBasic.setEmail("abcd@naver.com");
        userBasic.setName("김모씨");
        userBasic.setSocialType(SocialType.KAKAO);
        userBasicRepository.save(userBasic);

        UserBasic userBasic2 = new UserBasic();
        userBasic2.setUserId("777777");
        userBasic2.setEmail("7777@naver.com");
        userBasic2.setName("황모씨");
        userBasic2.setSocialType(SocialType.NAVER);
        userBasicRepository.save(userBasic2);
    }

}
