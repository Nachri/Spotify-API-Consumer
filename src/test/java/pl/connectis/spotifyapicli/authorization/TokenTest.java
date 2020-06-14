package pl.connectis.spotifyapicli.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TokenTest {

    private Token newToken;
    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void init() {
        newToken = new Token();
        newToken.setAccessToken("ThisIsMockTokenValue");
        newToken.setExpiresInSecond(Integer.MAX_VALUE);
        newToken.setExpirationTimeInMillisecondsBasedOnGenerationTimeAndExpiresInSeconds();
        newToken.setTokenType("Bearer");
    }

    @Test
    public void ReadAndSaveTokenSame() {
        tokenService.saveTokenToFile(newToken);
        Token readToken = tokenService.getToken();

        assertEquals(newToken, readToken);
        tokenService.deleteTokenFile();
    }

    @Test
    public void TokenHasExpired() {
        Token expiredToken = new Token();
        expiredToken.setExpirationTimeMillis(System.currentTimeMillis() - 1000L);

        assertFalse(tokenService.isTokenValid(expiredToken));
    }

    @Test
    public void TokenIsValid() {
        assertTrue(tokenService.isTokenValid(newToken));
    }


}
