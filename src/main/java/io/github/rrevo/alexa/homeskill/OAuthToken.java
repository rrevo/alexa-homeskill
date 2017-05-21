package io.github.rrevo.alexa.homeskill;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

/**
 * OAuth implementation to generate a access_token and refresh_token according to
 * https://tools.ietf.org/html/rfc6750#section-4.
 *
 * No verification of the code or secret is done.
 *
 */
public class OAuthToken extends BaseRequestStreamHandler {

    @Override
    public String handleLambdaRequest(String request, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();

        String responseCode = "200";

        JSONObject headerJson = new JSONObject();
        headerJson.put("Content-Type", "application/json");

        JSONObject responseBody = new JSONObject();
        responseBody.put("access_token", "secret-token");
        responseBody.put("expires_in", 60);
        responseBody.put("refresh_token", "uber-refresh-token");

        responseJson.put("statusCode", responseCode);
        responseJson.put("headers", headerJson);
        responseJson.put("body", responseBody.toString());

        return responseJson.toJSONString();
    }
}