package com.lycmoons.entity.vo.request;
import lombok.Data;

@Data
public class AskForCodeVO {
    String email;
    String type;   // "register" | "reset"
}
