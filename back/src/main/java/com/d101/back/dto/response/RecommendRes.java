package com.d101.back.dto.response;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendRes {
    private List<Long> foods;
}
