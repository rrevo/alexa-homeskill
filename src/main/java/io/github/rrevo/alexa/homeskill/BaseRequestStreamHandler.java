package io.github.rrevo.alexa.homeskill;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.*;
import java.util.stream.Collectors;

public abstract class BaseRequestStreamHandler implements RequestStreamHandler {

    @Override
    public final void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String request = reader.lines().collect(Collectors.joining());
        logger.log(request);

        String response = handleLambdaRequest(request, context);
        logger.log(response);

        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(response);
        writer.close();
    }

    protected abstract String handleLambdaRequest(String request, Context context) throws IOException;
}
