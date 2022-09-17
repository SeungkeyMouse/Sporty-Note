package com;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.Enum.SocialType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.service.*;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

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
    private final RecordService recordService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void run(String... args) throws Exception {
        //하단 메소드 안의 내용들을 주석처리하면 실행시 데이터가 주입되지 않습니다.
        System.out.println("Initializer.java : CommandLineRunner 실행");
        gymService.save(new GymDto("바디스페이스", "37", "128", "서울시", "강남구", "선릉로"));
        gymService.save(new GymDto("정원헬스장", "36", "127", "서울시", "성북구", "안암로"));
        gymService.save(new GymDto("의정부헬스장", "37.5", "128", "의정부시", "무슨구", "땡땡동"));
        gymService.save(new GymDto("선릉헬스장", "37.5", "126", "서울시", "강남구", "선릉동"));
        userBasicSetup();
        machineSetup();
        machineLocationSetup();
        noteSetup();
//        printUserFavorite();
        printUserFavorite();
        routineSetup();
        recordSetup();
        System.out.println(jwtTokenProvider.createAccessToken("12312312"));
        nodeSetDto();

        System.out.println("실행 완료되었습니다.");
    }

    private void nodeSetDto() {
        noteService.addNoteNodeSet(new NodeSetCreateDto(1L, NodeType.CHEST, "Orange", "세팅된 머신1의 내용1입니다.", 0F, 0F, LocalDateTime.now(), "www.naver.com"));
        noteService.addNoteNodeSet(new NodeSetCreateDto(1L, NodeType.CHEST, "Orange", "세팅된 머신1의 내용2입니다.", 0F, 0F, LocalDateTime.now(), "www.naver.com"));
        noteService.addNoteNodeSet(new NodeSetCreateDto(2L, NodeType.BACK, "Red", "세팅된 머신2의 내용2입니다.", 0F, 0F, LocalDateTime.now(), "www.naver.com"));
        noteService.addNoteNodeSet(new NodeSetCreateDto(2L, NodeType.ELBOW, "Blue", "세팅된 머신2의 내용2입니다.", 0F, 0F, LocalDateTime.now(), "www.naver.com"));

    }

    private void noteSetup() {
        /***
         * 노트한 기구 추가
         */
        noteService.addNoteNode("12312312",new NodeCreateDto(1L, 1L, NodeType.CHEST, "Orange", "12312312의 벤치프레스 Orange 내용1입니다", 0F, 0F, "사진주소1"));
        noteService.addNoteNode("12312312",new NodeCreateDto(1L, 1L, NodeType.CHEST, "Orange", "12312312의 벤치프레스 Orange 내용2입니다", 0F, 0F, "사진주소2"));
        noteService.addNoteNode("12312312",new NodeCreateDto(2L, 2L, NodeType.BACK, "Red", "12312312의 랫풀다운 Red 내용입니다", 13.5F, 20F, "사진주소3"));
        noteService.addNoteNode("12312312",new NodeCreateDto(4L, 2L, NodeType.BACK, "Red", "78978978의 랫풀다운 Red 내용입니다", 13.5F, 20F, "사진주소1"));

    }

    private void machineLocationSetup() {
        machineService.addNodeLocation(new NodeLocationDto(1L, NodeType.CHEST, 10F, 10F));
        machineService.addNodeLocation(new NodeLocationDto(1L, NodeType.BACK, 20F, 20F));
        machineService.addNodeLocation(new NodeLocationDto(1L, NodeType.ELBOW, 30F, 30F));

        machineService.addNodeLocation(new NodeLocationDto(2L, NodeType.CHEST, 100F, 10F));
        machineService.addNodeLocation(new NodeLocationDto(2L, NodeType.BACK, 200F, 20F));
        machineService.addNodeLocation(new NodeLocationDto(2L, NodeType.ELBOW, 300F, 30F));
    }

    /**
     * 즐겨찾기 출력
     */
    void printUserFavorite() {
        List<UserFavorite> userFavoriteMachines = userFavoriteRepository.findAll();
        for (UserFavorite userFavorite : userFavoriteMachines) {
            System.out.println("유저명: " + userFavorite.getUserBasic().getName() + "기구명 : " + userFavorite.getMachine().getKrMachineName());
        }
    }

    void machineSetup() {
        Machine machine = Machine.createMachine("벤치프레스", "Bench Press", "가슴", "이미지1주소", "이미지2주소", "비디오1주소");
        machineRepository.save(machine);

        Machine machine2 = Machine.createMachine("랫풀다운","Lat Pull Down","등","https://www.daum.net", "이미지2주소", "비디오주소");
        machineRepository.save(machine2);

        Machine machine3 = Machine.createMachine("스쿼트","Squat","하체","https://www.daum.net", "이미지2주소", "비디오주소1");
        machineRepository.save(machine3);

        Machine machine4 = Machine.createMachine("케이블 풀 다운","Cable pull down","등","머신4-이미지1", "이미지2주소", "비디오주소1");
        machineRepository.save(machine4);

        /***
         * 즐겨찾기 추가
         */
        machineService.addFavorite("12312312", 1L);
        machineService.addFavorite("12312312", 2L);
        machineService.addFavorite("78978978", 3L);


    }

    void userBasicSetup() {
        //String email, String oauthId, String name, String userId, SocialType socialType
        UserBasic userBasic;
        userBasic = UserBasic.createdUserBasic("abcd@naver.com", "10952728", "김모씨", "12312312", SocialType.GOOGLE);
        userBasicRepository.save(userBasic);
        userBasic = UserBasic.createdUserBasic("efgh@naver.com", "24093052", "황모씨", "45645645", SocialType.KAKAO);
        userBasicRepository.save(userBasic);
        userBasic = UserBasic.createdUserBasic("ijkl@naver.com", "35098016", "이모씨", "78978978", SocialType.GOOGLE);
        userBasicRepository.save(userBasic);
    }

    void routineSetup() {
        List<Long> machines = new ArrayList<>();
        for (long i = 1; i < 5; i++) {
            machines.add(i);
        }
        RoutineDto routineDto = new RoutineDto("12312312", "lower", machines);
        routineService.addRoutine(routineDto);
    }

    void recordSetup() {
        recordService.addRecord(new RecordDto("12312312", 1L, 1, 10, 10, true));
        recordService.addRecord(new RecordDto("45645645", 1L, 2, 15, 10, true));
        recordService.addRecord(new RecordDto("78978978", 1L, 3, 20, 10, true));
        recordService.addRecord(new RecordDto("12312312", 1L, 4, 25, 10, true));

    }


}