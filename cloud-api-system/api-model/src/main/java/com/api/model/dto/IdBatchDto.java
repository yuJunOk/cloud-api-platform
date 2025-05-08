package com.api.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author pengYuJun
 */
@Data
public class IdBatchDto implements Serializable {
    /**
     * id
     */
    private List<Long> idList;

    @Serial
    private static final long serialVersionUID = 1L;
}
