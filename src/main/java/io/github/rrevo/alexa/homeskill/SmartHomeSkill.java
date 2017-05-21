package io.github.rrevo.alexa.homeskill;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static org.json.simple.parser.ParseException.ERROR_UNEXPECTED_CHAR;

/**
 * Smart Home skill with hardcoded device and turn on and off interactions. This is an AWS Lambda
 * port of an example - https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/steps-to-create-a-smart-home-skill#create-a-lambda-function
 *
 * The name of the device is "Red Truck" and it can be turned on or off after deployment.
 *
 */
public class SmartHomeSkill extends BaseRequestStreamHandler {

    private JSONParser parser = new JSONParser();

    public static final String DISCOVERY = "Alexa.ConnectedHome.Discovery";
    public static final String CONTROL = "Alexa.ConnectedHome.Control";
    public static final String CONTROL_ON = "TurnOnRequest";
    public static final String CONTROL_OFF = "TurnOffRequest";

    @Override
    public String handleLambdaRequest(String request, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();

        StringBuffer responseJson = new StringBuffer();

        try {
            JSONObject body = (JSONObject) parser.parse(request);

            String accessToken = ((JSONObject) body.get("payload")).get("accessToken").toString();
            validate(logger, accessToken);

            JSONObject header = (JSONObject) body.get("header");
            String name = header.get("name").toString();
            String namespace = header.get("namespace").toString();

            if (namespace.equals(DISCOVERY)) {
                responseJson.append("{" +
                        "    \"header\": {" +
                        "        \"name\": \"DiscoverAppliancesResponse\"," +
                        "        \"namespace\": \"Alexa.ConnectedHome.Discovery\"," +
                        "        \"payloadVersion\": \"2\"" +
                        "    }," +
                        "    \"payload\": {" +
                        "        \"discoveredAppliances\": [" +
                        "            {" +
                        "                \"actions\": [" +
                        "                    \"turnOn\"," +
                        "                    \"turnOff\"" +
                        "                ]," +
                        "                \"additionalApplianceDetails\": {}," +
                        "                \"applianceId\": \"device001\"," +
                        "                \"friendlyDescription\": \"Virtual Device\"," +
                        "                \"friendlyName\": \"Red Truck\"," +
                        "                \"isReachable\": true," +
                        "                \"manufacturerName\": \"Big Co\"," +
                        "                \"modelName\": \"model x\"," +
                        "                \"version\": \"v1\"" +
                        "            }" +
                        "        ]" +
                        "    }" +
                        "}");
            } else if (namespace.equals(CONTROL)) {

                if (name.equals(CONTROL_ON)) {
                    responseJson.append("{" +
                            "        \"header\": {" +
                            "            \"name\": \"TurnOnConfirmation\"," +
                            "            \"namespace\": \"Alexa.ConnectedHome.Control\"," +
                            "            \"payloadVersion\": \"2\"" +
                            "        }," +
                            "        \"payload\": {}" +
                            "    }");
                } else if (name.equals(CONTROL_OFF)) {
                    responseJson.append("{" +
                            "        \"header\": {" +
                            "            \"name\": \"TurnOffConfirmation\"," +
                            "            \"namespace\": \"Alexa.ConnectedHome.Control\"," +
                            "            \"payloadVersion\": \"2\"" +
                            "        }," +
                            "        \"payload\": {}" +
                            "    }");
                } else {
                    throw new ParseException(ERROR_UNEXPECTED_CHAR, "Illegal control: " + name);
                }
            } else {
                throw new ParseException(ERROR_UNEXPECTED_CHAR, "Illegal namespace: " + namespace);
            }
        } catch (ParseException pex) {
            logger.log(pex.toString());
            responseJson.append("{ \"statusCode\" : \"400\"}");
        }

        return responseJson.toString();
    }

    private void validate(LambdaLogger logger, String accessToken) {
        logger.log("Access Token: " + accessToken);
    }
}
