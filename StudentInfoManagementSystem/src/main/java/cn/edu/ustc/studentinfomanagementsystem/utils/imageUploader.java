package cn.edu.ustc.studentinfomanagementsystem.utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class imageUploader {
    private static final String TOKEN_URL = "https://www.imgtp.com/api/token";
    private static final String UPLOAD_URL = "https://www.imgtp.com/api/upload";
    private String AUTHORIZATION;

    imageUploader(String email, String password) {
        // get api token
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(TOKEN_URL);
            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair("email", email));
            formParams.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(uefEntity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity responseEntity = response.getEntity();
                    String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    // extract code and token from the response string
                    // {"code":200,"msg":"success","data":{"token":"fbd42b37dc03fc18717890fea3160d1d"},"time":1717051958}
                    JSONObject jsonObject = new JSONObject(responseString);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        AUTHORIZATION = dataObj.getString("token");
                    } else {
                        System.err.println("Get token failed. Code: " + code);
                    }
                } else {
                    System.err.println("Get token failed. Status code: " + statusCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    imageUploader() {
        AUTHORIZATION = "fbd42b37dc03fc18717890fea3160d1d";
    }

    public String uploadImage(File file) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost httpPost = new HttpPost(UPLOAD_URL);
            // specify token
            StringBody token = new StringBody(AUTHORIZATION, ContentType.TEXT_PLAIN);
            // upload file
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image", file)
                    .addTextBody("token", AUTHORIZATION, ContentType.TEXT_PLAIN)
                    .build();
            HttpUriRequest postRequest = RequestBuilder
                    .post(UPLOAD_URL)
                    .setEntity(entity)
                    .build();
            System.out.println("Executing request " + postRequest.getRequestLine());
//            httpPost.setEntity(entity);
            // send the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                // 200: OK
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity responseEntity = response.getEntity();
                    String responseString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    // extract code and url from the response string
                    JSONObject jsonObject = new JSONObject(responseString);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        return dataObj.getString("url");
                    } else {
                        return "Upload failed. Code: " + code;
                    }
                } else {
                    return "Upload failed. Status code: " + statusCode;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        // path to a picture
        String oldFilePath = "src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/Wolffe.jpg";
        // copy the picture
        try {
            fileManager.copyFile(oldFilePath, "src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/Wolffe.jpg1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create a directory for the student
        String dirPath = "src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/";
        String studentID = "PB21111738";
        try {
            fileManager.createDirectory(dirPath + studentID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // move and rename the picture
        String newFilePath = dirPath + studentID + "/" + studentID + ".jpg";
        try {
            fileManager.renameFile(oldFilePath, newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // upload the picture
        File file = new File(newFilePath);
        imageUploader uploader = new imageUploader("z1057247521@hotmail.com", "123456");
        System.out.println(uploader.uploadImage(file));
        // rename the copy
        try {
            fileManager.renameFile("src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/Wolffe.jpg1", oldFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
