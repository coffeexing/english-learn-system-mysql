package cn.example.demo.common.tools.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * HTTP 客户端请求工具类
 * </P>
 */
public class HttpClientUtils {
    /**
     * <p>
     * JDK 8 URLConnection
     * </P>
     *
     * @param requestURL
     * @param jsonParam
     * @return JsonString
     * @throws IOException
     */
    public static String sendRequest(String requestURL, String jsonParam) throws IOException {
        // Instance a URLConnection
        URLConnection urlConnection = new URL(requestURL).openConnection();
        // Config the param of connection
        urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json, */*; q=0.01");
        urlConnection.setConnectTimeout(20000);
        urlConnection.setDoOutput(true);

        // Write the data to RequestStream
        OutputStream requestOutputStream = urlConnection.getOutputStream();
        requestOutputStream.write(jsonParam.getBytes(StandardCharsets.UTF_8));

        // Handle the ResponseStream
        String jsonResponse = new String(processResponseStream(urlConnection.getInputStream()), StandardCharsets.UTF_8);

        return jsonResponse;
    }

    /**
     * <p>
     * JDK 8 URLConnection
     * </P>
     *
     * @param requestURL
     * @return JsonString
     * @throws IOException
     */
    public static byte[] sendDownloadRequest(String requestURL) throws IOException {
        // Instance a URLConnection
        URLConnection urlConnection = new URL(requestURL).openConnection();
        // Config the param of connection
        urlConnection.setRequestProperty("Accept", "application/octet-stream, */*; q=0.01");
        urlConnection.setConnectTimeout(20000);

        // Handle the ResponseStream
        return processResponseStream(urlConnection.getInputStream());
    }

    /**
     * <p>
     * 处理响应流
     * </P>
     *
     * @param responseInputStream
     * @return
     * @throws IOException
     */
    protected static byte[] processResponseStream(InputStream responseInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int i;
        while ((i = responseInputStream.read(buffer, 0, buffer.length)) != -1) {
            byteArrayOutputStream.write(buffer, 0, i);
        }

        responseInputStream.close();
        byteArrayOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * <P>
     *     JDK 11 HttpClients
     * </P>
     * @param requestURL
     * @param jsonBody
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
//    public static String sendRequest(String requestURL, String jsonBody) throws IOException, InterruptedException {
//        // HttpClient
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_2)
//                .connectTimeout(Duration.ofSeconds(20))
//                .build();
//
//        // HttpRequest
//        HttpRequest request = HttpRequest.newBuilder(URI.create(GlobalAuthCenterURL.TOKEN_AUTH_URL))
//                .timeout(Duration.ofSeconds(20))
//                .header("Content-Type", "application/json;charset=UTF-8")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        return response.body;
//    }
}
