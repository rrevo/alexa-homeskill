package io.github.rrevo.alexa.homeskill;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URLDecoder;

/**
 * AWS Lambda function for OAuth authorization for Alexa. Flow steps are documented in
 * https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/linking-an-alexa-user-with-a-user-in-your-system#how-end-users-set-up-account-linking-for-a-skill
 *
 * No login screen is sent to the user. Given a correct set of query params (state and redirect_uri)
 * a http 302 redirect is generated with a hard coded code.
 */
public class OAuthAuthorize extends BaseRequestStreamHandler {

    private JSONParser parser = new JSONParser();

    @Override
    protected String handleLambdaRequest(String request, Context context) throws IOException {
        JSONObject responseJson = new JSONObject();
        String state = "";
        String redirectUri = "";

        try {
            JSONObject event = (JSONObject) parser.parse(request);
            if (event.get("queryStringParameters") != null) {
                JSONObject qps = (JSONObject) event.get("queryStringParameters");
                if (qps.get("state") != null) {
                    state = (String) qps.get("state");
                }
                if (qps.get("redirect_uri") != null) {
                    redirectUri = (String) qps.get("redirect_uri");
                    redirectUri = URLDecoder.decode(redirectUri, "UTF-8");
                }
            }

            String responseCode = "302";
            String code = "golden-ticket";
            String responselocation = redirectUri + "?code=" + code + "&state=" + state;

            JSONObject headerJson = new JSONObject();
            headerJson.put("Location", responselocation);

            responseJson.put("statusCode", responseCode);
            responseJson.put("headers", headerJson);

        } catch (ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        }
        return responseJson.toJSONString();
    }
}