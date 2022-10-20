//package com.sportynote.server.util;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.ECDSASigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.bouncycastle.util.io.pem.PemObject;
//import org.bouncycastle.util.io.pem.PemReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.core.io.ClassPathResource;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.ECPrivateKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.*;
//
//// 애플 로그인 util
//public class AppleUtil {
//    private static final Logger logger = LoggerFactory.getLogger(AppleUtil.class);
//    private static ObjectMapper objectMapper = new ObjectMapper();
//
//    public String createClientSecret(String teamId, String clientId, String keyId, String keyPath, String authUrl) throws NoSuchAlgorithmException {
//        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
//        JWTClaimsSet claimsSet = new JWTClaimsSet();
//        Date now = new Date();
//
//        claimsSet.setIssuer(teamId);
//        claimsSet.setIssueTime(now);
//        claimsSet.setExpirationTime(new Date(now.getTime() + 3600000));
//        claimsSet.setAudience(authUrl);
//        claimsSet.setSubject(clientId);
//
//        SignedJWT jwt = new SignedJWT(header, claimsSet);
//
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readPrivateKey(keyPath));
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        try {
//            ECPrivateKey ecPrivateKey = (ECPrivateKey) kf.generatePrivate(spec);
//            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
//            jwt.sign(jwsSigner);
//        } catch (InvalidKeySpecException | JOSEException e) {
//            throw new RuntimeException(e);
//        }
//
//        return jwt.serialize();
//    }
//
//
//    private byte[] readPrivateKey(String keyPath) {
//
//        ClassPathResource resource = new ClassPathResource(keyPath);
//        byte[] content = null;
//
//        try (Reader keyReader = new InputStreamReader(resource.getInputStream());
//             PemReader pemReader = new PemReader(keyReader)) {
//            {
//                PemObject pemObject = pemReader.readPemObject();
//                content = pemObject.getContent();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return content;
//    }
//
//    public String doPost(String url, Map<String, String> param) {
//        String result = null;
//        CloseableHttpClient httpclient = null;
//        CloseableHttpResponse response = null;
//        Integer statusCode = null;
//        String reasonPhrase = null;
//        try {
//            httpclient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//            List<NameValuePair> nvps = new ArrayList<>();
//            Set<Map.Entry<String, String>> entrySet = param.entrySet();
//            for (Map.Entry<String, String> entry : entrySet) {
//                String fieldName = entry.getKey();
//                String fieldValue = entry.getValue();
//                nvps.add(new BasicNameValuePair(fieldName, fieldValue));
//            }
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
//            httpPost.setEntity(formEntity);
//            response = httpclient.execute(httpPost);
//            statusCode = response.getStatusLine().getStatusCode();
//            reasonPhrase = response.getStatusLine().getReasonPhrase();
//            HttpEntity entity = response.getEntity();
//            result = EntityUtils.toString(entity, "UTF-8");
//
//            if (statusCode != 200) {
//                logger.error("[error] : " + result);
//            }
//            EntityUtils.consume(entity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//                if (httpclient != null) {
//                    httpclient.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    public JSONObject decodeFromIdToken(String id_token) {
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(id_token);
//            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
//            ObjectMapper objectMapper = new ObjectMapper();
//            JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);
//
//            if (payload != null) {
//                return payload;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//}