package com.nfttv.apis.config;

import com.nfttv.apis.nosql.domain.Playlist;
import com.nfttv.apis.nosql.repository.NFTCacheRepository;
import com.nfttv.apis.nosql.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationConfiguration {

    private PlaylistRepository playlistRepository;

    private NFTCacheRepository nftCacheRepository;

    @EventListener
    public void initializePublicPlaylists(ContextRefreshedEvent event) {
        if (playlistRepository.findByType(Playlist.PlaylistType.GENERAL).size() == 0 ) {
            final Playlist playlist = new Playlist();
            playlist.setType(Playlist.PlaylistType.GENERAL);
            playlist.setName(Playlist.PlaylistType.GENERAL.name());

            playlistRepository.save(playlist);
        }

    }




}
