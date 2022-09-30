package com.sportynote.server.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum NodeType {
    BACK("Back", "등/허리"),
    CHEST("Chest","가슴"),
    LEG("Leg", "다리"),
    SHOULDER("Shoulder", "어깨"),
    GRIP("Grip", "그립"),
    ELBOW("Elbow", "팔꿈치"),
//    ABDOMINAL("복부"),
    GAZE("Gaze", "시선"),
    HIP("Hip", "힙"),
    Etc("Etc", "기타");


    private final String engName;
    private final String krName;
    NodeType(String engName, String krName) {
        this.engName = engName;
        this.krName = krName;
    }
    public static NodeType findNodeType(String s){
        NodeType[] types = NodeType.values();
        for (NodeType type : types) {
            if(type.krName.equals(s) || type.engName.equals(s)) return type;
        }

        return NodeType.Etc;
    }

    @JsonCreator
    public static NodeType fromJson(@JsonProperty("engName") String engName, @JsonProperty("krName") String krName){
        return findNodeType(engName);
    }
}
