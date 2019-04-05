package handlers;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import java.util.Arrays;

public class MockContext implements Context {

    public static final int MOCK_VALUE = 0;

    @Override
    public String getAwsRequestId() {
        return "mockCallgetAwsRequestId";
    }

    @Override
    public String getLogGroupName() {
        return "mockCallgetLogGroupName";
    }

    @Override
    public String getLogStreamName() {
        return "mockCallgetLogStreamName";
    }

    @Override
    public String getFunctionName() {
        return "mockCallgetFunctionName";
    }

    @Override
    public String getFunctionVersion() {
        return "mockCallGetFunctionVersion";
    }

    @Override
    public String getInvokedFunctionArn() {
        return "mockCallgetInvokedFunctionArn";
    }

    @Override
    public CognitoIdentity getIdentity() {
        return null;
    }

    @Override
    public ClientContext getClientContext() {
        return null;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return MOCK_VALUE;
    }

    @Override
    public int getMemoryLimitInMB() {
        return MOCK_VALUE;
    }

    @Override
    public LambdaLogger getLogger() {
        return new LambdaLogger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }

            @Override
            public void log(byte[] message) {
                System.out.print(Arrays.toString(message));
            }
        };
    }
}
