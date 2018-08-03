package com.qs.game.auth;

import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zun.wei on 2018/8/3 18:49.
 * Description: JWT 工具类
 * JWT是json web token缩写。
 * 它将用户信息加密到token里，服务器不保存任何用户信息。
 * 服务器通过使用保存的密钥验证token的正确性，只要正确即通过验证。
 */
public class JWTUtils {


    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj
     * token 过期时间: 10天
     */
    public static final String SECRET = "JKKLJOoasdlfj";
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param user_id 登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            return null;
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        if (Objects.isNull(claims)) return null;
        Claim user_id_claim = claims.get("user_id");
        if (Objects.isNull(user_id_claim) || StringUtils.isBlank(user_id_claim.asString())) {
            return null;
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }

    public static void main(String[] args) throws Exception {
        String token = JWTUtils.createToken(100L);
        System.out.println("token = " + token);
        Map<String, Claim> claimMap = JWTUtils.verifyToken(token + 1);

        Long userID = JWTUtils.getAppUID(token + 1);
        System.out.println("userID = " + userID);
    }
}
