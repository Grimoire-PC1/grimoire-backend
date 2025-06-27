package com.grimoire.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistMusicCreateRequestDto {
    @JsonProperty("nome")
    private String name;

    @JsonProperty("link")
    private String link;
}
