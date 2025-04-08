package com.tregubov.firstserver.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCartOrFavoritesDTO {

    private UUID accountId;

    private int productId;

}
