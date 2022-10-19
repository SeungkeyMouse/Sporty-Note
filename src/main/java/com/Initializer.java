package com;

import com.sportynote.server.type.NodeType;
import com.sportynote.server.type.SocialType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
//        gymService.save(new GymDto("바디스페이스", "37", "128", "서울시", "강남구", "선릉로"));
//        gymService.save(new GymDto("정원헬스장", "36", "127", "서울시", "성북구", "안암로"));
//        gymService.save(new GymDto("의정부헬스장", "37.5", "128", "의정부시", "무슨구", "땡땡동"));
//        gymService.save(new GymDto("선릉헬스장", "37.5", "126", "서울시", "강남구", "선릉동"));
//        userBasicSetup();
//          machineSetup();//<-----------서버 시작시 실행
//        machineLocationSetup();
//        noteSetup();
////        printUserFavorite();
//        printUserFavorite();
//        routineSetup();
//        recordSetup();
//        System.out.println(jwtTokenProvider.createAccessToken("12312312"));
//        nodeSetDto();//<-----------서버 시작시 실행
        System.out.println("실행 완료되었습니다.");
        System.out.println(new Date());
    }

    private void nodeSetDto() {
        //작업장1
        noteService.addNoteNodeSet(new NodeSetCreateDto("어시스트 풀업",NodeType.SHOULDER,"#99FF99","올라갈때 어깨가 웅크리지 않도록 한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 벤치프레스",NodeType.GRIP,"#0000FF","어깨너비의 1.5배로 잡는다. 인체 구조상 그립 너비가 넓어야 가슴 근육을 사용하게 된다. 좁으면 손목,팔꿈치에 무리가 간다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 데드리프트",NodeType.GAZE,"#9933FF","준비자세는 45도 아래쪽을 바라본다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 풀 스쿼트",NodeType.LEG,"#FFFF66","발을 적당히 넓게 벌려주고, 발끝도 약간 바깥으로 벌려준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 인클라인 벤치프레스",NodeType.BACK,"#FF9999","날개뼈를 뒤로 접고 가슴을 내밀어줌(견갑 후인하강)", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 라잉 트라이셉스 익스텐션",NodeType.GRIP,"#0000FF","바벨을 척골 위에 얹어두는 느낌으로 잡는다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("암 풀 다운",NodeType.SHOULDER,"#99FF99","어깨가 절대 올라가면 안된다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 해머 컬",NodeType.ELBOW,"#CCFFFF","옆구리에 팔꿈치를 붙여준 상태로 앞뒤,옆으로 빠지지 않게 당겨준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 플라이",NodeType.CHEST,"#FFCC66","가슴을 펴고 날개뼈를 내린다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 프론트 레이즈",NodeType.SHOULDER,"#99FF99","어깨를 들쑥날쑥하지 않은 상태로 눌러준 상태로 운동한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 인클라인 벤치프레스",NodeType.HIP,"#FF6699","엉덩이를 등받이쪽으로 밀어 넣어준 상태로 진행한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 래터럴 레이즈",NodeType.GRIP,"#0000FF","주전자 따르듯이 하지않고(내회전 금지), 외회전을 유지한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 런지",NodeType.LEG,"#FFFF66","무릎은 발가락 방향으로 앞으로 포물선으로 편하게 내려간다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그 익스텐션",NodeType.BACK,"#FF9999","상체는 고정해준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("리버스 펙덱 플라이",NodeType.ETC,"#CCCCCC","밀어줄때, 180도 넘지 않게 주의한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 시티드 로우",NodeType.BACK,"#FF9999","허리를 중립을 유지시켜준 상태에서 조금 눕는다는 느낌으로 당겨준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 컬",NodeType.LEG,"#FFFF66","다리너비는 어깨너비정도 벌려준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 밀리터리 프레스",NodeType.GRIP,"#0000FF","손 너비를 넓게 잡아야 삼두가 아니라 전면 삼각근에 집중할 수 있다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("숄더프레스 머신",NodeType.ELBOW,"#CCFFFF","올릴때는 팔꿈치를 끝까지 피지 말고 진행하여 어깨가 들리지 않게한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그프레스 머신(경사)",NodeType.LEG,"#FFFF66","발은 골반넓이만큼 벌려준다.", 0F, 0F, LocalDateTime.now(), ""));

        //작업장2
        noteService.addNoteNodeSet(new NodeSetCreateDto("어시스트 풀업",NodeType.BACK,"#FF9999","허리가 꺾이지 않게 주의하며, 보조할 경우에는 허리가 아니라 등근육을 잡아줘야 한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 벤치프레스",NodeType.LEG,"#FFFF66","발은 바닥에서 떼지 말고 살작 넓게 벌려서 땅에 고정한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 데드리프트",NodeType.ELBOW,"#CCFFFF","준비자세는 팔을 최대한 핀 상태로 키커진다 생각하면서 상체를 최대한 세워준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 풀 스쿼트",NodeType.SHOULDER,"#99FF99","바벨이 어깨의 두툼한 부분에 걸치게 한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 인클라인 벤치프레스",NodeType.ELBOW,"#CCFFFF","호를 그리며 천천히 내려오며 팔꿈치가 지면과 수직으로 밀 것", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 라잉 트라이셉스 익스텐션",NodeType.BACK,"#FF9999","견갑을 거상하여 고정해야 팔이 완전히 접힌다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("암 풀 다운",NodeType.ELBOW,"#CCFFFF","팔꿈치를 살짝 바깥으로 돌려준 상태로 골반쪽으로 당겨준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 해머 컬",NodeType.GRIP,"#0000FF","엄지가 하늘을 향하는 느낌으로 당겨준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 플라이",NodeType.ELBOW,"#CCFFFF","팔꿈치를 조금 굽히고 땅을 보도록 천천히 내린다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 프론트 레이즈",NodeType.GRIP,"#0000FF","손의 각도는 전면 삼각근의 자극이 잘 오는 각도로 자유롭게 진행한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 인클라인 벤치프레스",NodeType.BACK,"#FF9999","견갑골은 너무 강하게 후인하강하는 것이 아니라 가슴이 열릴정도만 해주면 된다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 래터럴 레이즈",NodeType.BACK,"#FF9999","상체를 숙인 상태에서 덤벨을 들어올려 측면삼각근이 중력의 영향을 100% 받을 수 있게 한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 런지",NodeType.BACK,"#FF9999","앞으로 무릎을 내밀때 상체를 세운다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그 익스텐션",NodeType.LEG,"#FFFF66","발끝은 몸쪽으로, 허벅지는 누른 상태로 끝까지 밀어준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("리버스 펙덱 플라이",NodeType.ELBOW,"#CCFFFF","팔꿈치는 약간 굽힌 채로 밀어준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 시티드 로우",NodeType.GRIP,"#0000FF","스트레이트 바로는 언더그립으로 잡아준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 컬",NodeType.CHEST,"#FFCC66","가슴은 활짝 열어준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 밀리터리 프레스",NodeType.ELBOW,"#CCFFFF","바벨을 부러뜨린다는 느낌으로 팔꿈치의 방향이 옆이 아닌 앞을 향하도록 당겨준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("숄더프레스 머신",NodeType.ELBOW,"#CCFFFF","내릴때는 팔꿈치를 90도까지만 내려온다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그프레스 머신(경사)",NodeType.BACK,"#FF9999","허리 말리지않게 등받이 붙이고,허리를 펴준다.", 0F, 0F, LocalDateTime.now(), ""));

        //작업장3
        noteService.addNoteNodeSet(new NodeSetCreateDto("어시스트 풀업",NodeType.ELBOW,"#CCFFFF","팔을 완전히 핀 상태에서 바를 바깥쪽으로 구부려준다고 생각하고, 아래로쪽으로 내려주면서 겨드랑이쪽에 붙여준다는 느낌으로 당긴다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 벤치프레스",NodeType.BACK,"#FF9999","허리는 바닥에서 살짝 띄워서 아치모양을 만들어준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 데드리프트",NodeType.LEG,"#FFFF66","바닥이 벽이라고 생각하고 못을 뽑는 다는 느낌으로 벽을 밀면서 일어난다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 풀 스쿼트",NodeType.BACK,"#FF9999","무게중심은 중앙보다 살짝 앞쪽으로 가야 허리를 숙이지 않을 수 있다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 인클라인 벤치프레스",NodeType.ELBOW,"#CCFFFF","팔꿈치 각도는 45~90도 사이", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 라잉 트라이셉스 익스텐션",NodeType.ELBOW,"#CCFFFF","완전히 접고 펴주며 수직이동하며, 아프면 견갑 하강이 되어있기 때문이다.", 0F, 0F, LocalDateTime.now(), ""));
        //6
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 해머 컬",NodeType.SHOULDER,"#99FF99","어깨가 올라가지 않게 주의한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 플라이",NodeType.ELBOW,"#CCFFFF","이두근이 가슴에 밀착하도록 끝까지 수축해준다.", 0F, 0F, LocalDateTime.now(), ""));
        //9
        noteService.addNoteNodeSet(new NodeSetCreateDto("덤벨 인클라인 벤치프레스",NodeType.ELBOW,"#CCFFFF","올라갈때는 덤벨을 모아준다는 느낌으로 덤벨부터 밀면서 올라간다.", 0F, 0F, LocalDateTime.now(), ""));
        //11
        //12
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그 익스텐션",NodeType.ETC,"#CCCCCC","다리와 의자 사이 공간이 없을정도로 등받이를 조절한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("리버스 펙덱 플라이",NodeType.CHEST,"#FFCC66","가슴은 앞으로 둥글게 말고 진행한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 시티드 로우",NodeType.HIP,"#FF6699","누울 때 엉덩이는 따라가면 안되고 고정해준다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("케이블 컬",NodeType.ELBOW,"#CCFFFF","겨드랑이쪽에 붙여서 고정해준 상태로 진행한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("바벨 밀리터리 프레스",NodeType.BACK,"#FF9999","허리가 과하게 꺾이지 않기 위해 중립 상태로 유지한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("숄더프레스 머신",NodeType.GRIP,"#0000FF","손목 꺾이지 않고 일자로, 손바닥에 무게를 두고 진행한다.", 0F, 0F, LocalDateTime.now(), ""));
        noteService.addNoteNodeSet(new NodeSetCreateDto("레그프레스 머신(경사)",NodeType.HIP,"#FF6699","고관절 접히면서 엉덩이 늘어나는 느낌으로 당긴다.", 0F, 0F, LocalDateTime.now(), ""));

        //노드 추가로 db에 들어가는지 확인 필요
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
        List<Machine> machineList = new ArrayList<>();

        //운동기구 리스트
        machineList.add(Machine.createMachine("어시스트 딥스" , "Assisted Chest Dip (kneeling)" , "가슴" , "00091101/00091101_medium1.png" , "00091101/00091101_medium2.png" , "video/00091201_video1.mp4"));
        machineList.add(Machine.createMachine("어시스트 풀업" , "Assisted Pull-up" , "등" , "00171101/00171101_medium1.png" , "00171101/00171101_medium2.png" , "video/00171201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 벤치프레스" , "Barbell Bench Press" , "가슴" , "00251101/00251101_medium1.png" , "00251101/00251101_medium2.png" , "video/00251201_video1.mp4"));
        machineList.add(Machine.createMachine("벤트오버 바벨로우" , "Barbell Bent Over Row" , "등" , "00271101/00271101_medium1.png" , "00271101/00271101_medium2.png" , "video/00271201_video1.mp4"));
        machineList.add(Machine.createMachine("클린앤프레스" , "Barbell Clean and Press" , "어깨" , "00281101/00281101_medium1.png" , "00281101/00281101_medium2.png" , "video/00281201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 컬" , "Barbell Curl" , "팔" , "00311101/00311101_medium1.png" , "00311101/00311101_medium2.png" , "video/00311201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 데드리프트" , "Barbell Deadlift" , "Hips" , "00321101/00321101_medium1.png" , "00321101/00321101_medium2.png" , "video/00321201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 프론트 레이즈" , "Barbell Front Raise" , "어깨" , "00411101/00411101_medium1.png" , "00411101/00411101_medium2.png" , "video/00411201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 프론트 스쿼트" , "Barbell Front Squat" , "하체" , "00421101/00421101_medium1.png" , "00421101/00421101_medium2.png" , "video/00421201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 풀 스쿼트" , "Barbell Full Squat" , "하체" , "00431101/00431101_medium1.png" , "00431101/00431101_medium2.png" , "video/00431201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 인클라인 벤치프레스" , "Barbell Incline Bench Press" , "가슴" , "00471101/00471101_medium1.png" , "00471101/00471101_medium2.png" , "video/00471201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 인클라인 로우" , "Barbell Incline Row" , "등" , "00491101/00491101_medium1.png" , "00491101/00491101_medium2.png" , "video/00491201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 런지" , "Barbell Lunge" , "하체" , "00541101/00541101_medium1.png" , "00541101/00541101_medium2.png" , "video/00541201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 라잉 트라이셉스 익스텐션" , "Barbell Lying Triceps Extension Skull Crusher" , "팔" , "00601101/00601101_medium1.png" , "00601101/00601101_medium2.png" , "video/00601201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 인클라인 컬" , "Barbell Prone Incline Curl" , "팔" , "00721101/00721101_medium1.png" , "00721101/00721101_medium2.png" , "video/00721201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 슈러그" , "Barbell Shrug" , "등" , "00951101/00951101_medium1.png" , "00951101/00951101_medium2.png" , "video/00951201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 벤트오버 로우" , "Barbell Underhand Bent-over Row" , "등" , "01181101/01181101_medium1.png" , "01181101/01181101_medium2.png" , "video/01181201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 업라이트 로우" , "Barbell Upright Row" , "어깨" , "01211101/01211101_medium1.png" , "01211101/01211101_medium2.png" , "video/01211201_video1.mp4"));
        machineList.add(Machine.createMachine("암 풀 다운" , "Cable Bar Lateral Pulldown" , "등" , "01501101/01501101_medium1.png" , "01501101/01501101_medium2.png" , "video/01501201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 프론트 레이즈" , "Cable Front Raise" , "어깨" , "01621101/01621101_medium1.png" , "01621101/01621101_medium2.png" , "video/01621201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 해머 컬" , "Cable Hammer Curl (with rope) " , "팔" , "01651101/01651101_medium1.png" , "01651101/01651101_medium2.png" , "video/01651201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 래터럴 레이즈" , "Cable Lateral Raise" , "어깨" , "01781101/01781101_medium1.png" , "01781101/01781101_medium2.png" , "video/01781201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 로우 플라이" , "Cable Low Fly" , "가슴" , "01791101/01791101_medium1.png" , "01791101/01791101_medium2.png" , "video/01791201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 원 암 벤트오버 로우" , "Cable One Arm Bent over Row" , "등" , "01891101/01891101_medium1.png" , "01891101/01891101_medium2.png" , "video/01891201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 원 암 래터럴 레이즈" , "Cable One Arm Lateral Raise" , "어깨" , "01921101/01921101_medium1.png" , "01921101/01921101_medium2.png" , "video/01921201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 푸시다운" , "Cable Pushdown (with rope attachment)" , "팔" , "02001101/02001101_medium1.png" , "02001101/02001101_medium2.png" , "video/02001201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 슈러그" , "Cable Shrug" , "등" , "02201101/02201101_medium1.png" , "02201101/02201101_medium2.png" , "video/02201201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 로우 " , "Cable Standing Rear Delt Row (with rope)" , "등" , "02331101/02331101_medium1.png" , "02331101/02331101_medium2.png" , "video/02331201_video1.mp4"));
        machineList.add(Machine.createMachine("체스트 딥스" , "Chest Dip" , "가슴" , "02511101/02511101_medium1.png" , "02511101/02511101_medium2.png" , "video/02511201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 바이셉스 컬" , "Dumbbell Alternate Biceps Curl" , "팔" , "02851101/02851101_medium1.png" , "02851101/02851101_medium2.png" , "video/02851201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 벤치 프레스" , "Dumbbell Bench Press" , "가슴" , "02891101/02891101_medium1.png" , "02891101/02891101_medium2.png" , "video/02891201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 벤치 시티드 프레스" , "Dumbbell Bench Seated Press" , "어깨" , "02901101/02901101_medium1.png" , "02901101/02901101_medium2.png" , "video/02901201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 벤트오버 로우" , "Dumbbell Bent-over Row" , "등" , "02921101/02921101_medium1.png" , "02921101/02921101_medium2.png" , "video/02921201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 컨센트레이션 컬" , "Dumbbell Concentration Curl" , "팔" , "02971101/02971101_medium1.png" , "02971101/02971101_medium2.png" , "video/02971201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 플라이" , "Dumbbell Fly" , "가슴" , "03081101/03081101_medium1.png" , "03081101/03081101_medium2.png" , "video/03081201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 프론트 레이즈" , "Dumbbell Front Raise" , "어깨" , "03101101/03101101_medium1.png" , "03101101/03101101_medium2.png" , "video/03101201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 해머 컬" , "Dumbbell Hammer Curl (version 2)" , "팔" , "03121101/03121101_medium1.png" , "03121101/03121101_medium2.png" , "video/03121201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 인클라인 벤치프레스" , "Dumbbell Incline Bench Press" , "가슴" , "03141101/03141101_medium1.png" , "03141101/03141101_medium2.png" , "video/03141201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 인클라인 컬" , "Dumbbell Incline Curl" , "팔" , "03181101/03181101_medium1.png" , "03181101/03181101_medium2.png" , "video/03181201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 인클라인 플라이" , "Dumbbell Incline Fly" , "가슴" , "03191101/03191101_medium1.png" , "03191101/03191101_medium2.png" , "video/03191201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 인클라인 해머 컬" , "Dumbbell Incline Hammer Curl" , "팔" , "03201101/03201101_medium1.png" , "03201101/03201101_medium2.png" , "video/03201201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 인클라인 로우" , "Dumbbell Incline Row" , "등" , "03271101/03271101_medium1.png" , "03271101/03271101_medium2.png" , "video/03271201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 킥백" , "Dumbbell Kickback" , "팔" , "03331101/03331101_medium1.png" , "03331101/03331101_medium2.png" , "video/03331201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 래터럴 레이즈" , "Dumbbell Lateral Raise" , "어깨" , "03341101/03341101_medium1.png" , "03341101/03341101_medium2.png" , "video/03341201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 런지" , "Dumbbell Lunge" , "하체" , "03361101/03361101_medium1.png" , "03361101/03361101_medium2.png" , "video/03361201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 라잉 트라이셉스 익스텐션" , "Dumbbell Lying Triceps Extension" , "팔" , "03511101/03511101_medium1.png" , "03511101/03511101_medium2.png" , "video/03511201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 리어 플라이" , "Dumbbell Rear Fly" , "어깨" , "03781101/03781101_medium1.png" , "03781101/03781101_medium2.png" , "video/03781201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 리어 래터럴 레이즈" , "Dumbbell Rear Lateral Raise" , "어깨" , "03801101/03801101_medium1.png" , "03801101/03801101_medium2.png" , "video/03801201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 시티드 프리처 컬" , "Dumbbell Seated Preacher Curl" , "팔" , "04021101/04021101_medium1.png" , "04021101/04021101_medium2.png" , "video/04021201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 시티드 숄더프레스" , "Dumbbell Seated Shoulder Press" , "어깨" , "04051101/04051101_medium1.png" , "04051101/04051101_medium2.png" , "video/04051201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 슈러그" , "Dumbbell Shrug" , "등" , "04061101/04061101_medium1.png" , "04061101/04061101_medium2.png" , "video/04061201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 오버헤드 프레스" , "Dumbbell Standing Overhead Press" , "어깨" , "04261101/04261101_medium1.png" , "04261101/04261101_medium2.png" , "video/04261201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 트라이셉스 익스텐션" , "Dumbbell Standing Triceps Extension" , "팔" , "04301101/04301101_medium1.png" , "04301101/04301101_medium2.png" , "video/04301201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 업라이트 로우" , "Dumbbell Upright Row" , "어깨" , "04371101/04371101_medium1.png" , "04371101/04371101_medium2.png" , "video/04371201_video1.mp4"));
        machineList.add(Machine.createMachine("이지바 바벨 컬" , "EZ Barbell Curl" , "팔" , "04471101/04471101_medium1.png" , "04471101/04471101_medium2.png" , "video/04471201_video1.mp4"));
        machineList.add(Machine.createMachine("이지바 인클라인 트라이셉스 익스텐션" , "EZ Barbell Incline Triceps Extension" , "팔" , "04491101/04491101_medium1.png" , "04491101/04491101_medium2.png" , "video/04491201_video1.mp4"));
        machineList.add(Machine.createMachine("체스트 프레스 머신(원판)" , "Lever Chest Press (plate loaded)" , "가슴" , "05761101/05761101_medium1.png" , "05761101/05761101_medium2.png" , "video/05761201_video1.mp4"));
        machineList.add(Machine.createMachine("체스트 프레스 머신" , "Lever Chest Press" , "가슴" , "05771101/05771101_medium1.png" , "05771101/05771101_medium2.png" , "video/05771201_video1.mp4"));
        machineList.add(Machine.createMachine("하이 로우 머신" , "Lever High Row (plate loaded)" , "등" , "05811101/05811101_medium1.png" , "05811101/05811101_medium2.png" , "video/05811201_video1.mp4"));
        machineList.add(Machine.createMachine("래터럴 레이즈 머신" , "Lever Lateral Raise" , "어깨" , "05841101/05841101_medium1.png" , "05841101/05841101_medium2.png" , "video/05841201_video1.mp4"));
        machineList.add(Machine.createMachine("레그 익스텐션" , "Lever Leg Extension" , "하체" , "05851101/05851101_medium1.png" , "05851101/05851101_medium2.png" , "video/05851201_video1.mp4"));
        machineList.add(Machine.createMachine("라잉 레그 컬 머신" , "Lever Lying Leg Curl" , "하체" , "05861101/05861101_medium1.png" , "05861101/05861101_medium2.png" , "video/05861201_video1.mp4"));
        machineList.add(Machine.createMachine("시티드로우 머신(원판)" , "Lever Neutral Grip Seated Row (plate loaded)" , "등" , "05881101/05881101_medium1.png" , "05881101/05881101_medium2.png" , "video/05881201_video1.mp4"));
        machineList.add(Machine.createMachine("프리처 컬 머신" , "Lever Preacher Curl" , "팔" , "05921101/05921101_medium1.png" , "05921101/05921101_medium2.png" , "video/05921201_video1.mp4"));
        machineList.add(Machine.createMachine("시티드 플라이 머신" , "Lever Seated Fly" , "가슴" , "05961101/05961101_medium1.png" , "05961101/05961101_medium2.png" , "video/05961201_video1.mp4"));
        machineList.add(Machine.createMachine("시티드 레그 컬 머신" , "Lever Seated Leg Curl" , "하체" , "05991101/05991101_medium1.png" , "05991101/05991101_medium2.png" , "video/05991201_video1.mp4"));
        machineList.add(Machine.createMachine("리버스 펙덱 플라이" , "Lever Seated Reverse Fly" , "어깨" , "06021101/06021101_medium1.png" , "06021101/06021101_medium2.png" , "video/06021201_video1.mp4"));
        machineList.add(Machine.createMachine("티바 로우 머신(원판)" , "Lever T-bar Row (plate loaded)" , "등" , "06061101/06061101_medium1.png" , "06061101/06061101_medium2.png" , "video/06061201_video1.mp4"));
        machineList.add(Machine.createMachine("트라이셉스 익스텐션 머신" , "Lever Triceps Extension" , "팔" , "06071101/06071101_medium1.png" , "06071101/06071101_medium2.png" , "video/06071201_video1.mp4"));
        machineList.add(Machine.createMachine("풀업" , "Pull-up" , "등" , "06521101/06521101_medium1.png" , "06521101/06521101_medium2.png" , "video/06521201_video1.mp4"));
        machineList.add(Machine.createMachine("푸시업" , "Push-up" , "가슴" , "06621101/06621101_medium1.png" , "06621101/06621101_medium2.png" , "video/06621201_video1.mp4"));
        machineList.add(Machine.createMachine("레그프레스 머신(경사45)" , "Sled 45 Leg Press" , "하체" , "07391101/07391101_medium1.png" , "07391101/07391101_medium2.png" , "video/07391201_video1.mp4"));
        machineList.add(Machine.createMachine("핵 스쿼트 머신" , "Sled Hack Squat" , "하체" , "07431101/07431101_medium1.png" , "07431101/07431101_medium2.png" , "video/07431201_video1.mp4"));
        machineList.add(Machine.createMachine("벤치프레스(스미스 머신)" , "Smith Bench Press" , "가슴" , "07481101/07481101_medium1.png" , "07481101/07481101_medium2.png" , "video/07481201_video1.mp4"));
        machineList.add(Machine.createMachine("인클라인 벤치프레스(스미스 머신)" , "Smith Incline Bench Press" , "가슴" , "07571101/07571101_medium1.png" , "07571101/07571101_medium2.png" , "video/07571201_video1.mp4"));
        machineList.add(Machine.createMachine("트라이셉스 딥스" , "Triceps Dip" , "팔" , "08141101/08141101_medium1.png" , "08141101/08141101_medium2.png" , "video/08141201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 시티드 로우" , "Cable seated row" , "등" , "08611101/08611101_medium1.png" , "08611101/08611101_medium2.png" , "video/08611201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 컬" , "Cable Curl" , "팔" , "08681101/08681101_medium1.png" , "08681101/08681101_medium2.png" , "video/08681201_video1.mp4"));
        machineList.add(Machine.createMachine("팩덱 플라이 머신" , "Lever Pec Deck Fly" , "가슴" , "10301101/10301101_medium1.png" , "10301101/10301101_medium2.png" , "video/10301201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 밀리터리 프레스" , "Barbell Standing Military Press (without rack)" , "어깨" , "11651101/11651101_medium1.png" , "11651101/11651101_medium2.png" , "video/11651201_video1.mp4"));
        machineList.add(Machine.createMachine("라잉 티바 로우 머신" , "Lever Lying T-bar Row" , "등" , "11941101/11941101_medium1.png" , "11941101/11941101_medium2.png" , "video/11941201_video1.mp4"));
        machineList.add(Machine.createMachine("맨몸 스쿼트" , "Bodyweight Squat (male)" , "하체" , "11971101/11971101_medium1.png" , "11971101/11971101_medium2.png" , "video/11971201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 랫 푸시다운" , "Cable Standing Lat Pushdown (rope equipment)" , "등" , "12291101/12291101_medium1.png" , "12291101/12291101_medium2.png" , "video/12291201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 스탠딩 크로스오버" , "Cable Standing-Up Straight Crossovers" , "가슴" , "12691101/12691101_medium1.png" , "12691101/12691101_medium2.png" , "video/12691201_video1.mp4"));
        machineList.add(Machine.createMachine("인클라인 체스트프레스 머신" , "Lever Incline Chest Press" , "가슴" , "12991101/12991101_medium1.png" , "12991101/12991101_medium2.png" , "video/12991201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 와이드 그립 리어 풀다운" , "Cable Wide Grip Rear Pulldown Behind Neck" , "등" , "13251101/13251101_medium1.png" , "13251101/13251101_medium2.png" , "video/13251201_video1.mp4"));
        machineList.add(Machine.createMachine("친업" , "Chin-Up" , "등" , "13261101/13261101_medium1.png" , "13261101/13261101_medium2.png" , "video/13261201_video1.mp4"));
        machineList.add(Machine.createMachine("로우 바 스쿼트(스미스머신)" , "Smith Low Bar Squat" , "하체" , "14341101/14341101_medium1.png" , "14341101/14341101_medium2.png" , "video/14341201_video1.mp4"));
        machineList.add(Machine.createMachine("숄더프레스 머신" , "Lever Seated Shoulder Press" , "어깨" , "14541101/14541101_medium1.png" , "14541101/14541101_medium2.png" , "video/14541201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 스쿼트" , "Dumbbell Squat" , "하체" , "15551101/15551101_medium1.png" , "15551101/15551101_medium2.png" , "video/15551201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 트라이셉스 푸시다운(SZ바)" , "Cable Triceps Pushdown (SZ-bar)" , "팔" , "16051101/16051101_medium1.png" , "16051101/16051101_medium2.png" , "video/16051201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 크로스 바디 해머 컬" , "Dumbbell Cross Body Hammer Curl (Version 2)" , "팔" , "16571101/16571101_medium1.png" , "16571101/16571101_medium2.png" , "video/16571201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 로프 하이풀리 오버헤드 트라이셉스 익스텐션" , "Cable Rope High Pulley Overhead Tricep Extension" , "팔" , "17241101/17241101_medium1.png" , "17241101/17241101_medium2.png" , "video/17241201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 라잉 싱글 익스텐션" , "Dumbbell Lying Single Extension" , "팔" , "17351101/17351101_medium1.png" , "17351101/17351101_medium2.png" , "video/17351201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 고블릿 스쿼트" , "Dumbbell Goblet Squat" , "하체" , "17601101/17601101_medium1.png" , "17601101/17601101_medium2.png" , "video/17601201_video1.mp4"));
        machineList.add(Machine.createMachine("래터럴 풀다운 머신(원판)" , "Lever Lateral Pulldown (plate loaded)" , "등" , "18701101/18701101_medium1.png" , "18701101/18701101_medium2.png" , "video/18701201_video1.mp4"));
        machineList.add(Machine.createMachine("덤벨 아놀드 프레스" , "Dumbbell Arnold Press" , "어깨" , "21371101/21371101_medium1.png" , "21371101/21371101_medium2.png" , "video/21371201_video1.mp4"));
        machineList.add(Machine.createMachine("레그프레스 머신(경사)" , "Lever Seated Leg Press" , "하체" , "22671101/22671101_medium1.png" , "22671101/22671101_medium2.png" , "video/22671201_video1.mp4"));
        machineList.add(Machine.createMachine("풀오버 머신(원판)" , "Lever Pullover (plate loaded)" , "등" , "22851101/22851101_medium1.png" , "22851101/22851101_medium2.png" , "video/22851201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 래터럴 풀다운(V바)" , "Cable Lateral Pulldown with V-bar" , "등" , "26161101/26161101_medium1.png" , "26161101/26161101_medium2.png" , "video/26161201_video1.mp4"));
        machineList.add(Machine.createMachine("이지바 라잉 풀오버" , "EZ-Bar Lying Bent Arms Pullover" , "등" , "30101101/30101101_medium1.png" , "30101101/30101101_medium2.png" , "video/30101201_video1.mp4"));
        machineList.add(Machine.createMachine("포워드 스쿼트" , "Forward Lunge" , "하체" , "34701101/34701101_medium1.png" , "34701101/34701101_medium2.png" , "video/34701201_video1.mp4"));
        machineList.add(Machine.createMachine("케이블 벤트오버 로우" , "Cable Bent Over Row" , "등" , "38591101/38591101_medium1.png" , "38591101/38591101_medium2.png" , "video/38591201_video1.mp4"));
        machineList.add(Machine.createMachine("원암 로우 머신(원판)" , "Lever One Arm Low Row (plate loaded)" , "등" , "42151101/42151101_medium1.png" , "42151101/42151101_medium2.png" , "video/42151201_video1.mp4"));
        machineList.add(Machine.createMachine("바벨 풀 오버" , "Barbell Pullover" , "등" , "43391101/43391101_medium1.png" , "43391101/43391101_medium2.png" , "video/43391201_video1.mp4"));
        machineList.add(Machine.createMachine("리버스 그립 하이로우 머신(원판)" , "Lever Reverse Grip High Row (plate loaded)" , "등" , "43781101/43781101_medium1.png" , "43781101/43781101_medium2.png" , "video/43781201_video1.mp4"));

        for (Machine machine : machineList) {
            List<Machine> byName = machineRepository.findByName(machine.getKrMachineName());
            if(byName.size()==0) machineRepository.save(machine);
        }



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
        for (long i = 1L; i < 5L; i++) {
            machines.add(i);
        }
        //RoutineDto routineDto = new RoutineDto( "lower", machines);
        //routineService.addRoutine("12312312",routineDto);
    }

    void recordSetup() {
        recordService.addRecord(new RecordDto("12312312", 1L, 1, 10, 10, true));
        recordService.addRecord(new RecordDto("45645645", 1L, 2, 15, 10, true));
        recordService.addRecord(new RecordDto("78978978", 1L, 3, 20, 10, true));
        recordService.addRecord(new RecordDto("12312312", 1L, 4, 25, 10, true));

    }


}