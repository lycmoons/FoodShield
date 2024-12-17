package com.lycmoons.entity.vo.response;

import com.lycmoons.entity.dto.FoodDTO;
import com.lycmoons.util.Convertor;
import lombok.Data;


@Data
public class FoodVO {
    Integer id;
    String name;
    String date;
    String manufacturer;
    Integer batch_num;
    String photo_url;


    public FoodVO(FoodDTO dto){
        id = dto.getId();
        name = dto.getName();
        date = Convertor.convertDate(dto.getDate());
        manufacturer = dto.getManufacturer();
        batch_num = dto.getBatch_num();
        photo_url = dto.getPhoto_url();
    }
}
