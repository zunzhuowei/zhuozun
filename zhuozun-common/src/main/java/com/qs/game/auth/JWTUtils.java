package com.qs.game.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qs.game.constant.StrConst;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zun.wei on 2018/8/3 18:49.
 * Description: JWT 工具类
 * JWT是json web token缩写。
 * 它将用户信息加密到token里，服务器不保存任何用户信息。
 * 服务器通过使用保存的密钥验证token的正确性，只要正确即通过验证。
 */
public class JWTUtils {


    /**
     * token秘钥，请勿泄露，请勿随便修改
     * token 过期时间: 10天
     */
    public static final String SECRET = "q1rhhQW%GxX6sYu6BZ1Q";
    //public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String USER_SIGN_KEY = "sKey";


    // header Map
    private static final Map<String, Object> HEADER = new HashMap<>();

    static {
        HEADER.put("alg", "HS256");
        HEADER.put("typ", "JWT");
    }

    /**
     * 生成 username 类型的token
     *
     * @param username userName
     * @return new token
     */
    public static String createToken(String username, String signKey) {
        return JWTUtils.createTokenObj(null, username, signKey);
    }


    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param userId 登录成功后用户userId, 参数userId不可传空
     */
    public static String createToken(Long userId, String signKey) {
        return JWTUtils.createTokenObj(userId, null, signKey);
    }

    /**
     *  生成token
     * @param userId userId
     * @param username userName
     * @return token
     */
    private static String createTokenObj(Long userId, String username,String sKey) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // build token
        // param backups {iss:Service, aud:APP}
        String token = null;
        if (Objects.nonNull(userId)) {
            token = JWT.create().withHeader(HEADER) // header
                    .withIssuer("Service") //jwt签发者  // payload
                    .withSubject("APP") //jwt所面向的用户
                    .withAudience("APP") //接收jwt的一方
                    .withClaim(USER_ID, userId.toString()) //自定义
                    .withClaim(USER_SIGN_KEY, sKey) //用户签名秘钥
                    .withIssuedAt(iatDate) // jwt的签发时间
                    .withExpiresAt(expiresDate) // jwt的过期时间，这个过期时间必须大于签发时间
                    .sign(Algorithm.HMAC256(SECRET)); // signature
        } else if (StringUtils.isNotBlank(username)) {
            token = JWT.create().withHeader(HEADER) // header
                    .withIssuer("Service") //jwt签发者  // payload
                    .withSubject("APP") //jwt所面向的用户
                    .withAudience("APP") //接收jwt的一方
                    .withClaim(USER_NAME, username)
                    .withClaim(USER_SIGN_KEY, sKey) //用户签名秘钥
                    .withIssuedAt(iatDate) // jwt的签发时间
                    .withExpiresAt(expiresDate) // jwt的过期时间，这个过期时间必须大于签发时间
                    .sign(Algorithm.HMAC256(SECRET)); // signature
        }
        return token;
    }

    /*
    iss：jwt签发者
    sub：jwt所面向的用户
    aud：接收jwt的一方
    exp：jwt的过期时间，这个过期时间必须大于签发时间
    nbf：定义在什么时间之前，该jwt都是不可用的
    iat：jwt的签发时间
    jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
     */

    /**
     * 解密Token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = getDecodeJWT(token);
        return Objects.isNull(jwt) ? null : jwt.getClaims();
    }

    /**
     *  根据token 解密jwt
     * @param token token
     * @return DecodedJWT
     */
    private static DecodedJWT getDecodeJWT(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return jwt;
    }

    /**
     * 获取过期日期
     */
    public static Date getExpiresAt(String token) {
        DecodedJWT jwt = getDecodeJWT(token);
        return Objects.isNull(jwt) ? null : jwt.getExpiresAt();
    }

    /**
     * 根据Token获取userId
     *
     * @return userId
     */
    public static Long getAppUID(String token) {
        String userId = JWTUtils.getClaimValueByTokenAndKey(token, USER_ID);
        return StringUtils.isBlank(userId) ? null : Long.valueOf(userId);
    }

    /**
     * 根据Token获取username
     *
     * @return username
     */
    public static String getAppUsername(String token) {
        return JWTUtils.getClaimValueByTokenAndKey(token, USER_NAME);
    }

    /**
     * 根据Token获取用户签名秘钥
     * @return sign key
     */
    public static String getSignKey(String token) {
        return JWTUtils.getClaimValueByTokenAndKey(token, USER_SIGN_KEY);
    }

    /**
     * 获取jwt参数实体类
     */
    public static JWTEntity getEntityByToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
        Map<String, Claim> claims = jwt.getClaims();
        if (Objects.isNull(claims)) return null;
        Claim username = claims.get(USER_NAME);
        Claim sKey = claims.get(USER_SIGN_KEY);
        Claim userId = claims.get(USER_ID);
        if (Objects.nonNull(username))
            return new JWTEntity().setExpDate(jwt.getExpiresAt())
                    .setSKey(sKey.asString()).setUName(username.asString());
        if (Objects.nonNull(userId))
            return new JWTEntity().setExpDate(jwt.getExpiresAt())
                    .setSKey(sKey.asString()).setUid(Long.valueOf(userId.asString()));
        return null;
    }

    /**
     * 根据Claim 的key 获取其值
     *
     * @param token Token
     * @param key   Claim key
     * @return Claim value
     */
    public static String getClaimValueByTokenAndKey(String token, String key) {
        Map<String, Claim> claims = verifyToken(token);
        if (Objects.isNull(claims)) return null;
        Claim claim = claims.get(key);
        if (Objects.isNull(claim) || StringUtils.isBlank(claim.asString())) {
            return null;
            // token 校验失败, 抛出Token验证非法异常
        }
        return claim.asString();
    }

   /* public static void main(String[] args) throws Exception {
        String token = JWTUtils.createToken(100L, "aa");
        System.out.println("token = " + token);
        Map<String, Claim> claimMap = JWTUtils.verifyToken(token + 1);

        Long userID = JWTUtils.getAppUID(token + 1);
        System.out.println("userID = " + userID);

        DecodedJWT decodedJWT = JWT.decode(token);
        List<String> list = decodedJWT.getAudience();
        for (String s : list) {
            System.out.println("s = " + s);
        }
        Map<String, Claim> claimMap1 = decodedJWT.getClaims();
        for (Map.Entry<String, Claim> stringClaimEntry : claimMap1.entrySet()) {
            System.out.println("stringClaimEntry = " + stringClaimEntry.getKey()
                    + " --- " + stringClaimEntry.getValue().asString());

        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("simpleDateFormat.format(decodedJWT.getExpiresAt()) = "
                + simpleDateFormat.format(decodedJWT.getExpiresAt()));
        System.out.println("\"token length\" = "
                + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBUFAiLCJhdWQiOiJBUFAiLCJpc3MiOiJTZXJ2aWNlIiwic0tleSI6ImFhIiwiZXhwIjoxNTM2NDY0MzUzLCJ1c2VySWQiOiIxMDAiLCJpYXQiOjE1MzU2MDAzNTN9.hSuCRIkR42eWRrOkmX0u9e-c6XOEo_aaWs5UTa08rfQ".length());
    }*/
}
