package com.tregubov.firstserver.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AddCommentDTO {

    private UUID accountId;
    private int productId;
    private int rating;
    private String advantage;
    private String disadvantage;
    private List<String> images;
    private List<String> videos;

}
