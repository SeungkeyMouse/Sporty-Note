package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name ="routine_table")
@SQLDelete(sql = "UPDATE routine_table SET deleted = true WHERE routine_idx = ?")
@Where(clause = "deleted=false")
public class Routine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="routine_idx")
    private Long idx;

    @NotNull
    private String routineName;

    @NotNull
    @JoinColumn(name= "user_id",referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserBasic userBasic;


    @OneToMany(mappedBy = "routine", cascade= CascadeType.ALL)
    private List<RoutineList> routineLists = new ArrayList<>();

    public Routine(String routineName, UserBasic userBasic){
        this.routineName=routineName;
        this.userBasic=userBasic;
    }

    @Builder //수정 빌더
    public Routine(Long idx, String routineName, UserBasic userBasic){
        this.idx=idx;
        this.routineName=routineName;
        this.userBasic=userBasic;
    }

    //==Routine 생성 메서드==//
    public static Routine createRoutine(String routineName, UserBasic userBasic) {
        return Routine.builder()
                .routineName(routineName)
                .userBasic(userBasic)
                .build();
    }


}
