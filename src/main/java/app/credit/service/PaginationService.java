package app.credit.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    public PageRequest pagination( Pageable pageable, Integer page, Sort sort) {
        PageRequest pageRequest;
        if (page != null && page > 0) {
            pageRequest = PageRequest.of(page, pageable.getPageSize(), sort);
        } else {
            pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return pageRequest;
    }
}
