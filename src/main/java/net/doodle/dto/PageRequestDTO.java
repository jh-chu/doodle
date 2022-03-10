package net.doodle.dto;

import lombok.Data;

@Data
public class PageRequestDTO {

    int page;
    int size;

    public PageRequestDTO() {
        page = 1;
        size = 10;
    }


}
