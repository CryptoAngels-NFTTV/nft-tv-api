package com.nfttv.apis;

import com.nfttv.apis.apiresource.playlist.CreatePlaylistApiResource;
import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.apiresource.playlist.PlaylistApiResource;
import com.nfttv.apis.apiresource.playlist.UpdatePlaylistApiResource;
import com.nfttv.apis.common.domain.NftTvUser;
import com.nfttv.apis.nosql.domain.Nft;
import com.nfttv.apis.nosql.domain.Playlist;
import com.nfttv.apis.services.NftService;
import com.nfttv.apis.services.PlaylistService;
import com.nfttv.apis.utils.NftUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/playlists")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlaylistsApi extends BaseApi {

    private PlaylistService playlistService;

    private NftService nftService;

    private NftUtils nftUtils;

    @GetMapping
    public ResponseEntity<List<PlaylistApiResource>> get() {
        final Optional<NftTvUser> loggedUser = getLoggerUser();
        final List<Playlist> playlists = playlistService.getUserPlaylist(loggedUser.isEmpty() ? "" : loggedUser.get().username);
        return ResponseEntity.ok(playlists.stream().map(this::mapPlaylistToApiResource).collect(Collectors.toList()));
    }

    @GetMapping("/{playlist}")
    public ResponseEntity<Page<NftMetadataApiResource>> getByIdentifier(@PathVariable("playlist") final String playlistIdentifier) {
        final Optional<Playlist> playlist = playlistService.getById(playlistIdentifier);
        Page<NftMetadataApiResource> response = Page.empty();
        if (playlist.isPresent()) {
            final List<Nft> nfts = nftService.filterByIds(playlist.get().getItems());
            response = new PageImpl<>(nfts.stream().map(nft ->
                    this.nftUtils.buildMetadataResource(nft)).collect(Collectors.toList()), Pageable.ofSize(nfts.size()), nfts.size());
        }
        return ResponseEntity.ok(response);
    }

    private PlaylistApiResource mapPlaylistToApiResource(final Playlist playlist) {
        return new PlaylistApiResource(playlist.getId().toHexString(),playlist.getName());
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistApiResource> createPlaylist(@RequestBody final CreatePlaylistApiResource content) {
        final Optional<NftTvUser> loggedUser = getLoggerUser();
        if (loggedUser.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        final Playlist playlist = playlistService.savePlaylist(Playlist.builder()
                .name(content.getName()).type(Playlist.PlaylistType.PUBLIC).items(content.getItems()).user(loggedUser.get().username).build());
        return ResponseEntity.ok(mapPlaylistToApiResource(playlist));
    }

    @PutMapping(path = "/{playlist}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistApiResource> updatePlaylist(@PathVariable("playlist") final String playlistIdentifier,
                                                              @RequestBody UpdatePlaylistApiResource content) {
        final Optional<NftTvUser> loggedUser = getLoggerUser();
        if (loggedUser.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        final Optional<Playlist> optionalPlaylist = playlistService.getById(playlistIdentifier);
        ResponseEntity<PlaylistApiResource> response = ResponseEntity.of(Optional.empty());
        if (optionalPlaylist.isPresent()) {
            final Playlist playlist = optionalPlaylist.get();
            playlist.getItems().addAll(content.getItems());
            response = ResponseEntity.ok(mapPlaylistToApiResource(playlistService.savePlaylist(playlist)));
        }
        return response;
    }

}
