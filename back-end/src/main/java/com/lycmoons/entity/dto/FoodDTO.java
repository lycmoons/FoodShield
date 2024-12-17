package com.lycmoons.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FoodDTO {
    Integer id;
    String name;
    Date date;
    String manufacturer;
    Integer batch_num;
    String photo_url;
}
