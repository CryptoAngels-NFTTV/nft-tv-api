package com.nfttv.apis.common.repository;

import com.nfttv.apis.common.domain.NftTvUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author rchiarinelli
 */
public interface NftTvUserRepository extends CrudRepository<NftTvUser, Integer> {

    /**
     *
     * @param username
     * @return
     */
    Optional<NftTvUser> findByUsername(final String username);

}
