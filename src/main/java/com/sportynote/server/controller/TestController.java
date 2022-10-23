package com.sportynote.server.controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import java.security.*;


@Controller
@Slf4j
public class TestController {

    public static final String TEAM_ID = "9NS2G9S8SM";
    public static final String REDIRECT_URL = "https://api.sportynote.com/gym/login/oauth_apple";
    public static final String CLIENT_ID = "com.sportynote.services";
    public static final String KEY_ID = "2RJ9H5FGB8";
    public static final String AUTH_URL = "https://appleid.apple.com";
    public static final String KEY_PATH = "static/apple/AuthKey_QS9UWDPNF3.p8";

    @GetMapping(value = "/login/getAppleAuthUrl")
    public @ResponseBody String getAppleAuthUrl(HttpServletRequest request) throws Exception {
        String reqUrl =
                AUTH_URL
                        + "/auth/authorize?client_id="
                        + CLIENT_ID
                        + "&redirect_uri="
                        + REDIRECT_URL
                        + "&response_type=code id_token&scope=name email&response_mode=form_post";
        return reqUrl;
    }
    @PostMapping(value = "/login/getAppleAuthUrl")
    public String getApplePLZ(){
        return "HI";
    }
    public String createClientSecret(String teamId, String clientId, String keyId, String keyPath, String authUrl) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        Date currentTime = new Date();

        claimsSet.setIssuer(teamId);
        claimsSet.setIssueTime(currentTime);
        claimsSet.setExpirationTime(new Date(currentTime.getTime() + 3600000));
        claimsSet.setAudience(authUrl);
        claimsSet.setSubject(clientId);

        SignedJWT jwt = new SignedJWT(header, claimsSet);
        log.info("createClientSecret; jwt={}", jwt);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readPrivateKey(keyPath));
        KeyFactory kf = KeyFactory.getInstance("EC");

        try {
            ECPrivateKey ecPrivateKey = (ECPrivateKey) kf.generatePrivate(spec);
            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());

            jwt.sign(jwsSigner);

        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return jwt.serialize();
}
    /*private byte[] readPrivateKey(String keyPath) throws IOException {
        Resource resource = new ClassPathResource(keyPath);
        byte[] content = null;

        try(FileReader keyReader = new FileReader(resource.getFile());
            PemReader pemReader = new PemReader(keyReader))
        {
            PemObject pemObject = pemReader.readPemObject();
            content = pemObject.getContent();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
        log.info("readPrivateKey; content={}", content);

        return content;
    }*/

    private byte[] readPrivateKey(String keyPath) {

        ClassPathResource resource = new ClassPathResource(keyPath);
        byte[] content = null;

        try (Reader keyReader = new InputStreamReader(resource.getInputStream());
             PemReader pemReader = new PemReader(keyReader)) {
            {
                PemObject pemObject = pemReader.readPemObject();
                content = pemObject.getContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public String doPost(String url, Map<String, String> param) {
        String result = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        Integer statusCode = null;
        String reasonPhrase = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = param.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue();
                nvps.add(new BasicNameValuePair(fieldName, fieldValue));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
            httpPost.setEntity(formEntity);
            response = httpclient.execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");

            if (statusCode != 200) {
                System.out.println("애러");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public JSONObject decodeFromIdToken(String id_token) {

        try {
            SignedJWT signedJWT = SignedJWT.parse(id_token);
            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
            String appleInfo = getPayload.toJSONObject().toJSONString();
            JSONObject payload = new JSONObject(appleInfo);

            if (payload != null) {
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
