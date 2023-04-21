package com.nfttv.apis.services;

import com.nfttv.apis.nosql.domain.Nft;
import com.nfttv.apis.nosql.domain.Playlist;
import com.nfttv.apis.nosql.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PlaylistService {

    private PlaylistRepository playlistRepository;

    private NftService nftService;

    public List<Playlist> getUserPlaylist(final String user) {
        return Stream.concat(getBasePlaylist().stream()
                , playlistRepository.findByUser(user).stream()).collect(Collectors.toList());
    }

    public List<Playlist> getBasePlaylist() {
        final List<Playlist> playlists = playlistRepository.findByType(Playlist.PlaylistType.GENERAL);
        return playlists;
    }

    public Optional<Playlist> getById(final String identifier) {
        final Optional<Playlist> playlist = playlistRepository.findById(new ObjectId(identifier));
        if (playlist.isPresent() && playlist.get().getType().equals(Playlist.PlaylistType.GENERAL)) {
            playlist.get().setItems(nftService.filterNft(Pageable.ofSize(100)).get().map(Nft::getId).map(ObjectId::toString).collect(Collectors.toSet()));
        }
        return playlist;
    }

    public Playlist savePlaylist(final Playlist playlist) {
        return playlistRepository.save(playlist);
    }

}
