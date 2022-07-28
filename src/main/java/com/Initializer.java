package com;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.Enum.SocialType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.service.GymService;
import com.sportynote.server.service.MachineService;
import com.sportynote.server.service.NoteService;
import com.sportynote.server.service.RoutineService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final RoutineService routineService;
    private final UserFavoriteRepository userFavoriteRepository;
    private final NoteService noteService;

    @Override
    public void run(String... args) throws Exception {
        //하단 메소드 안의 내용들을 주석처리하면 실행시 데이터가 주입되지 않습니다.
        System.out.println("Initializer.java : CommandLineRunner 실행");
        gymService.save(new GymDto("바디스페이스", "37", "128","서울시", "강남구", "선릉로"));
        gymService.save(new GymDto("정원헬스장", "36", "127","서울시", "성북구", "안암로"));
        gymService.save(new GymDto("의정부헬스장", "37.5", "128","의정부시", "무슨구", "땡땡동"));

        userBasicSetup();
        machineSetup();
        machineLocationSetup();
        noteSetup();
        printUserFavorite();
        System.out.println("실행 완료되었습니다.");
    }

    private void noteSetup() {
        /***
         * 노트한 기구 추가
         */
        noteService.addNoteNode(new NodeCreateDto("123123", 6, NodeType.CHEST,"Orange", "123123의 벤치프레스 Orange 내용1입니다", 0F,0F,"사진주소1"));
        noteService.addNoteNode(new NodeCreateDto("123123", 6,NodeType.CHEST, "Orange", "123123의 벤치프레스 Orange 내용2입니다", 0F,0F,"사진주소2"));
        noteService.addNoteNode(new NodeCreateDto("123123", 6, NodeType.BACK, "Red", "123123의 벤치프레스 Red 내용입니다", 13.5F,20F,"사진주소3"));
        noteService.addNoteNode(new NodeCreateDto("777777", 7,  NodeType.BACK, "Red", "777777의 랫풀다운 Red 내용입니다", 13.5F,20F,"사진주소1"));

    }

    private void machineLocationSetup() {
        machineService.addNodeLocation(new NodeLocationDto(6,NodeType.CHEST, 10F,10F));
        machineService.addNodeLocation(new NodeLocationDto(6,NodeType.BACK, 20F,20F));
        machineService.addNodeLocation(new NodeLocationDto(6,NodeType.ELBOW, 30F,30F));

        machineService.addNodeLocation(new NodeLocationDto(7,NodeType.CHEST, 100F,10F));
        machineService.addNodeLocation(new NodeLocationDto(7,NodeType.BACK, 200F,20F));
        machineService.addNodeLocation(new NodeLocationDto(7,NodeType.ELBOW, 300F,30F));
    }

    /**
     * 즐겨찾기 출력
     */
    void printUserFavorite() {
        List<UserFavorite> userFavoriteMachines = userFavoriteRepository.findAll();
        for (UserFavorite userFavorite : userFavoriteMachines) {
            System.out.println("유저명: " + userFavorite.getUserBasic().getName() +"기구명 : " + userFavorite.getMachine().getKrMachineName());
        }
    }

    void machineSetup() {
        Machine machine = new Machine();
        machine.setKrMachineName("벤치프레스");
        machine.setEngMachineName("Bench Press");
        machine.setTargetArea("가슴");
        machine.setUrl("https://www.naver.com");
        //machine.setGym(gymRepository.findAll().get(0));

        machineRepository.save(machine);

        Machine machine2 = new Machine();
        machine2.setKrMachineName("랫풀다운");
        machine2.setEngMachineName("Lat Pull Down");
        machine2.setTargetArea("등");
        machine2.setUrl("https://www.daum.net");
        // machine2.setGym(gymRepository.findAll().get(1));

        machineRepository.save(machine2);

        Machine machine3 = new Machine();
        machine3.setKrMachineName("스쿼트");
        machine3.setEngMachineName("Squat");
        machine3.setTargetArea("하체");
        machine3.setUrl("https://www.swmaestro.org/sw/main/main.do");
        // machine2.setGym(gymRepository.findAll().get(1));

        machineRepository.save(machine3);
        /***
         * 즐겨찾기 추가
         */
        machineService.addFavorite("123123", 6);
        machineService.addFavorite("123123", 7);

        machineService.addFavorite("777777", 8);


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