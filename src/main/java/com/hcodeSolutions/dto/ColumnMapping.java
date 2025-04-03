package com.hcodeSolutions.dto;

import lombok.*;

/**
 * @author Dewmith Mihisara
 * @date 2025-04-03
 * @since 0.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnMapping {
    private String excelColumn;
    private String dbColumn;
    private String dataType;
}
