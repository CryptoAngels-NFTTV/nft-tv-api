package com.nfttv.apis;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.google.common.base.Strings;
import com.nfttv.apis.common.domain.NftTvUser;
import com.nfttv.apis.common.repository.NftTvUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

/**
 * Provides base methods for API classes
 *
 * @author rchiarinelli
 */
@Slf4j
public abstract class BaseApi {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.managementApiToken}")
    private String managementApiToken;

    @Autowired
    private NftTvUserRepository userRepository;

    Optional<NftTvUser> getLoggerUser() {

        NftTvUser nftTvUser = null;

        if (JwtAuthenticationToken.class.isAssignableFrom(SecurityContextHolder.getContext().getClass())) {
            JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            final Jwt jwt = (Jwt) jwtAuthentication.getCredentials();
            final Optional<NftTvUser> nftTvUserOptional = userRepository.findByUsername(jwt.getSubject());
            nftTvUser = nftTvUserOptional.orElseGet(() -> {
                NftTvUser newUser = null;
                //check if user is DB through jwt.getSubject(), call ManagementAPI if not user is not at DB.
                try {
                    final ManagementAPI managementAPI = ManagementAPI.newBuilder(domain, managementApiToken).build();
                    final User user = managementAPI.users().get(jwt.getSubject(), new UserFilter()).execute().getBody();

                    newUser = new NftTvUser();
                    newUser.username = Strings.isNullOrEmpty(user.getUsername()) ? jwt.getSubject() : user.getUsername();
                    newUser.email = Strings.isNullOrEmpty(user.getEmail()) ? jwt.getSubject() : user.getEmail();
                    newUser.firstname = Strings.isNullOrEmpty(user.getGivenName()) ? jwt.getSubject() : user.getGivenName();
                    newUser.lastname = Strings.isNullOrEmpty(user.getFamilyName()) ? jwt.getSubject() : user.getFamilyName();

                    userRepository.save(newUser);

                } catch (Auth0Exception e) {
                    log.error("Exception retrieving logged user info: ", e);
                }
                return newUser;
            });
        }
        return Optional.ofNullable(nftTvUser);
    }
}
