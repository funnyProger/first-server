package com.tregubov.firstserver.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RemoveCommentDTO {

    private UUID accountId;
    private int productId;
    private int commentId;

}
