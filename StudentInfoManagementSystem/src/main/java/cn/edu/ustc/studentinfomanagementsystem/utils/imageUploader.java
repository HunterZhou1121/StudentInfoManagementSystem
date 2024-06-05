package cn.edu.ustc.studentinfomanagementsystem.utils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class imageUploader {
    private static final String UPLOAD_URL = "https://www.imgurl.org/api/v2/upload";
    private static final String AUTHORIZATION = "b9618b3a87522cec7a1ed000334e7186";

    private static final String UID = "081a11e22460efe0cebd44e8e6972137";

    private Connection connection;

    public imageUploader() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }

    public static @Nullable String uploadImage(File file) {
        if (!file.exists()) {
            return null;
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // upload file
            HttpPost httpPost = new HttpPost(UPLOAD_URL);
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", file)
                    .addTextBody("uid", UID, ContentType.TEXT_PLAIN)
                    .addTextBody("token", AUTHORIZATION, ContentType.TEXT_PLAIN)
                    .build();
            httpPost.setEntity(entity);
            System.out.println("Executing request " + httpPost.getRequestLine());
            // send the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
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
                        System.out.println("Upload failed. Code: " + code);
                        return null;
                    }
                } else {
                    System.out.println("Upload failed. Status code: " + statusCode);
                    return null;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static @Nullable String uploadImage(String path) {
        File file = new File(path);
        return uploadImage(file);
    }

    public  void uploadAllLocalImages() {
        // find all students in the database whose "PhotoURL" is null
        String sql = "SELECT Enrolment.StudentID FROM Enrolment, Student WHERE Enrolment.ID = Student.ID AND Student.PhotoURL IS NULL";
        // test connection
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String StudentID = rs.getString("StudentID");
                // the picture is in "src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/"
                String rootPath = "src/main/resources/cn/edu/ustc/studentinfomanagementsystem/pics/";
                String oldPicPath = rootPath + StudentID + ".jpg";
                File file = new File(oldPicPath);
                // continue if the file does not exist
                if (!file.exists()) {
                    continue;
                }
                // upload the picture
                String url = uploadImage(file);
                System.out.println("StudentID " + StudentID + " PhotoURL: " + url);
                // query the student's ID from Enrolment, using StudentID
                String querySQL = "SELECT ID FROM Enrolment WHERE StudentID = ?";
                String ID = "";
                try (PreparedStatement queryPS = connection.prepareStatement(querySQL)) {
                    queryPS.setString(1, StudentID);
                    try (ResultSet queryRS = queryPS.executeQuery()) {
                        if (queryRS.next()) {
                            ID = queryRS.getString("ID");
                        }
                    }
                } catch (SQLException e) {
                    DBConnection.SQLExceptionHandler(e);
                }
                // update the database
                String updateSQL = "UPDATE Student SET PhotoURL = ? WHERE ID = ?";
                try (PreparedStatement updatePS = connection.prepareStatement(updateSQL)) {
                    updatePS.setString(1, url);
                    updatePS.setString(2, ID);
                    updatePS.executeUpdate();
                } catch (SQLException e) {
                    DBConnection.SQLExceptionHandler(e);
                }
                // delete the local picture
                if (file.delete()) {
                    System.out.println("Deleted the file: " + file.getName());
                } else {
                    System.out.println("Failed to delete the file.");
                }
            }
        } catch (SQLException e) {
            DBConnection.SQLExceptionHandler(e);
        }
    }
    public static void main(String[] args) {
        imageUploader uploader = new imageUploader();
        uploader.uploadAllLocalImages();
    }

}
