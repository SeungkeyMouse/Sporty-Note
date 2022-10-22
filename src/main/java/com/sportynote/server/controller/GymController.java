//package com.sportynote.server.controller;
//
//import com.sportynote.server.service.GymService;
//import lombok.RequiredArgsConstructor;
//import org.bouncycastle.util.io.pem.PemObject;
//import org.bouncycastle.util.io.pem.PemReader;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.interfaces.ECPrivateKey;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/gym")
//public class GymController {
//    private final GymService gymService;
////    @GetMapping("/test")
////    public ResponseEntity<?> getUser() {
////        throw new RestApiException(MISMATCH_REFRESH_TOKEN);
////    }
//@GetMapping("/test")
//public Integer Test() throws IOException {
//    System.out.println(readPrivateKey("static/AuthKey_QS9UWDPNF3.p8"));
//    return 1;
//}
//    private byte[] readPrivateKey(String keyPath) throws IOException {
//        Resource resource = new ClassPathResource(keyPath);
//        byte[] content = null;
//
//        try(FileReader keyReader = new FileReader(resource.getFile());
//            PemReader pemReader = new PemReader(keyReader))
//        {
//            PemObject pemObject = pemReader.readPemObject();
//            content = pemObject.getContent();
//
//        } catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        return content;
//    }
//}
