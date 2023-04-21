package com.nfttv.apis.apiresource.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlaylistApiResource extends UpdatePlaylistApiResource {

    private String name;

}

