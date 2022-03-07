package net.doodle.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Slf4j
public class PageResultDTO<DTO> {

    //DTO 리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPages;
    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev,next;

    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<DTO> result) {
        totalPages = result.getTotalPages();

        dtoList = result.getContent();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPages > tempEnd ? tempEnd : totalPages;

        next = end < totalPages;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
