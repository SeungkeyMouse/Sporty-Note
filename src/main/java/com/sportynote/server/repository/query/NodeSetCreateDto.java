package com.sportynote.server.repository.query;

import com.sportynote.server.type.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Data
public class NodeSetCreateDto {
    //노드 저장할때 사용하는 용도 : "관리자"가 "노드"를 "특정 머신"에 저장한다

    //노드를 기구 노트에 이어주기 위한 정보
    private String machineName;

    //노드에 대한정보
    //종류
    private NodeType type;
    private String color;

    //내용
    private String text;

    private Float x_location;
    private Float y_location;

    private LocalDateTime updated_at;
    private String pictureUrl;

}
