package net.doodle.web.dto;

import lombok.Data;

@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String keyword;

    public PageRequestDTO() {
        page = 1;
        size = 10;
    }


}
