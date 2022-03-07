package net.doodle.dto;

import lombok.Data;

@Data
public class PageRequest {

    int page;
    int size;

    public PageRequest() {
        page = 1;
        size = 10;
    }


}
