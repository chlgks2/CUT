package com.example.controller;

import com.example.model.dto.responseDTO;



import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

@Controller
public class FindMusicController {
    //녹음 파일 전송
//    @PostMapping("/upload")
    public ResponseEntity<com.greedy.thymeleaf.controller.Message> recordupload(@RequestParam("data") MultipartFile wav) throws IOException {

        //통신

        byte[] array = wav.getBytes();


        String recordfile = Base64.getEncoder().encodeToString(array);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.add("file", recordfile);

        HttpEntity<MultiValueMap<String, String>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = "http://192.168.0.71:5000/";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate
                .postForEntity(serverUrl, requestEntity, String.class);


        System.out.println(response.getBody());

        String jsonData = (String) response.getBody();

//        ObjectMapper mapper = new ObjectMapper();
//        String jsonData = mapper.writeValueAsString(response.getBody());

        // 통신 x

        //String jsonData = "[{\"song_id\":50}, {\"song_id\":51}]";

        String data = "{ \"data\" : " + jsonData + "}";
        System.out.println("datadata : " + data);
        JSONParser parser = new JSONParser();
        Object ob = null;
        try {
            ob = parser.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> jsList = (List) js.get("data");

        System.out.println(jsList);
        System.out.println(jsList.get(0).get("song_id"));


        responseDTO dto = new responseDTO();

        long[] temp = new long[30];

        for(int i=0;i<jsList.size();i++){
            temp[i] = (long) jsList.get(i).get("song_id");
        }
        System.out.println(temp[0]);

        dto.setSongId(temp);

        System.out.println(dto.getSongId()[0]);






        return ResponseEntity.ok(new com.greedy.thymeleaf.controller.Message(200, "ok"));
    }
}

