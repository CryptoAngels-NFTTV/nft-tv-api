package com.nfttv.apis;

import com.nfttv.apis.apiresource.NftMetadataApiResource;
import com.nfttv.apis.apiresource.PlaylistApiResource;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
@Slf4j
@AllArgsConstructor
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
        return PlaylistApiResource.builder().id(playlist.getId().toHexString()).name(playlist.getName()).build();
    }

}
