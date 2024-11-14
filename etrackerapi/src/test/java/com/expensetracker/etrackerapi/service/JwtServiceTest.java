package com.expensetracker.etrackerapi.service;

import com.expensetracker.etrackerapi.service.implementation.JWTServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JWTServiceImpl jwtService;

    private String secretKey = "";

    @BeforeEach
    public void init(){
        jwtService = new JWTServiceImpl();
    }

    @Test
    public void JwtService_Constructor_ReturnSecretKey() throws Exception{
        Field secretKeyField = JWTServiceImpl.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);

        String secretKey = (String) secretKeyField.get(jwtService);

        Assertions.assertThat(secretKey).isNotNull();
        Assertions.assertThat(secretKey).isNotEmpty();
    }

    @Test
    public void JwtService_GenerateToken_ReturnToken() throws Exception{
        String username = "testUser";
        String token = jwtService.generateToken(username);

        Assertions.assertThat(token).isNotNull();
        Assertions.assertThat(token).startsWith("eyJ");
    }

    @Test
    public void JwtService_GetKey_ReturnKey() throws Exception{
        Field secretKeyField = JWTServiceImpl.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        String secretKey = (String) secretKeyField.get(jwtService);

        Method getKeyMethod = JWTServiceImpl.class.getDeclaredMethod("getKey");
        getKeyMethod.setAccessible(true);
        SecretKey key = (SecretKey) getKeyMethod.invoke(jwtService);

        byte[] decodeKeyByte = Decoders.BASE64.decode(secretKey);

        Assertions.assertThat(key).isNotNull();
        Assertions.assertThat(decodeKeyByte).isEqualTo(key.getEncoded());

    }

    @Test
    public void JwtService_ExtractClaim_ReturnClaim()throws Exception{
        Method getKeyMethod = JWTServiceImpl.class.getDeclaredMethod("getKey");
        Method getExtractClaim = JWTServiceImpl.class.getDeclaredMethod("extractClaim",String.class,Function.class);
        getExtractClaim.setAccessible(true);
        getKeyMethod.setAccessible(true);

        SecretKey key = (SecretKey) getKeyMethod.invoke(jwtService);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username","testUser");

        String token = Jwts.builder()
                .claims(claims)
                .subject("testUser")
                .signWith(key)
                .compact();

        Function<Claims, String> claimResolver = claims1 -> claims1.get("username", String.class);
        String extractedUsername = (String) getExtractClaim.invoke(jwtService, token, claimResolver);

        Assertions.assertThat(extractedUsername).isNotNull();
        Assertions.assertThat(extractedUsername).isEqualTo("testUser");
    }

}
