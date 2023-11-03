package asd.ecommercenew.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagingDto<T> {
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private List<T> content;

}