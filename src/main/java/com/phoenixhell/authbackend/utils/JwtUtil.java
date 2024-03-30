package com.phoenixhell.authbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.text.SimpleDateFormat;
import java.util.*;


public class JwtUtil {
    private static final long DEFAULT_EXPIRE = 1000 * 60 * 60 ; //过期时间 1小时

    //不能用动态生成 无法解密
    //public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);//密钥，动态生成的密钥
    public static final SecretKey key = Keys.hmacShaKeyFor("secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret".getBytes());//密钥，动态生成的密钥

    // 签名方法 HS256
    public static final SignatureAlgorithm signatureAlgorithm =SignatureAlgorithm.HS256;

    /**
     * 生成token
     *
     * @param claims 要传送消息map
     */
    public static String generate(HashMap<String, Object> claims, Long expireTimeMills, String username) {

        // 自定义字符串生成秘钥  我用Keys.secretKeyFor 自动生成了
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        //Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 生成JWT的时间
        long createTimeMills = System.currentTimeMillis();
        Date createTime = new Date(createTimeMills);

        //头部信息,可有可无
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "jwt");

        //更强的密钥,JDK11起才能用
        //  KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        //  PrivateKey key1 =  keyPair.getPrivate();  // 私钥
        //  PublicKey key2 =  keyPair.getPublic();  //公钥


        /**
         iss: jwt签发者
         sub: jwt所面向的用户
         aud: 接收jwt的一方
         exp: jwt的过期时间，这个过期时间必须要大于签发时间
         nbf: 定义在什么时间之前，该jwt都是不可用的.
         iat: jwt的签发时间
         jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
         */
        JwtBuilder builder = Jwts.builder().setHeader(header)
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，
                // 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置 jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //jwt的签发时间
                .setIssuedAt(createTime)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
                .setSubject(username)
                .signWith(key,signatureAlgorithm);

        if (expireTimeMills >= 0) {
            Date expireDate = new Date(createTimeMills+expireTimeMills);
            // 设置过期时间
            builder.setExpiration(expireDate);
        }
        return builder.compact();
    }

    /**
     * 生成默认过期时间token
     */
    public static String generate(Map<String, Object> claims, String username) {
        Date now = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + DEFAULT_EXPIRE);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(key,signatureAlgorithm)
                .compact();
    }

    /**
     * 校验是不是jwt签名
     *
     * @param token
     * @return
     */
    public static boolean isSigned(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .isSigned(token);
    }

    /**
     * 校验签名是否正确
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 获取payload 部分内容（即要传的信息）
     * 使用方法：如获取userId：getClaim(token).get("userId");
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            // @ControllerAdvice不能拦截Filter中的异常,
            // 因为@ControllerAdvice只是对Controller做了加强，而Filter在Controller之前进行

            // 不处理(没有authentication)直接由下面的异常处理器处理CustomAuthenticationEntryPoint
        }
        return claims;
    }

    /**
     * 获取头部信息map
     * 使用方法 : getHeader(token).get("alg");
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeader(String token) {
        JwsHeader header = null;
        try {
            header = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getHeader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        Date date = getClaim(token).getIssuedAt();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(date);
        System.out.println(formatDate);
        return date;
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        return getClaim(token).getExpiration();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 直接Base64解密获取header内容
     *
     * @param token
     * @return
     */
    public static String getHeaderByBase64(String token) {
        String header = null;
        if (isSigned(token)) {
            try {
                byte[] header_byte = Base64.getDecoder().decode(token.split("\\.")[0]);
                header = new String(header_byte);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return header;
    }

    /**
     * 直接Base64解密获取payload内容
     *
     * @param token
     * @return
     */
    public static String getPayloadByBase64(String token) {
        String payload = null;
        if (isSigned(token)) {
            try {
                byte[] payload_byte = Base64.getDecoder().decode(token.split("\\.")[1]);
                payload = new String(payload_byte);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return payload;
    }

    public static void main(String[] args) {
        //用户自定义信息claims
        //Map<String, Object> map = new HashMap<>();
        //map.put("permission", "admin");
        //String token = generate(map,"shadow");
        //System.out.println(token);
        String token="eyJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9uIjoiYWRtaW4iLCJqdGkiOiJjNjU2ODkyZC1iMDljLTRiZWMtOGFjYi02ZDM2M2E3YWE2M2MiLCJpYXQiOjE3MTE3ODQ0NzMsInN1YiI6InNoYWRvdyIsImV4cCI6MTcxMTc4ODA3M30.L1X5dgaDTiiNXLau7gLIuevQRLw_k1koHZu4XSxdqQY";
        System.out.println("claim:" + getClaim(token).get("permission"));
        System.out.println("header:" + getHeader(token));
        //    System.out.println(getIssuedAt(token));
        Claims claims = getClaim(token);

        //  System.out.println(getHeaderByBase64(token));
        System.out.println(getPayloadByBase64(token));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
        System.out.println("当前时间:" + sdf.format(new Date()));
    }
}

