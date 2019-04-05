package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import java.util.Optional;
import no.bibsys.aws.lambda.events.DeployEvent;
import no.bibsys.aws.lambda.handlers.templates.CodePipelineCommunicator;
import no.bibsys.aws.lambda.handlers.templates.CodePipelineFunctionHandlerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePipelineLambdaFunction extends CodePipelineFunctionHandlerTemplate<SampleOutputObject> {

    private static final Logger logger = LoggerFactory.getLogger(SimplePipelineLambdaFunction.class);
    private static final String NULL_INPUT_OBJECT = "null input Object";
    private static final String NULL_INPUT_REQUEST = "null input request";
    public static final String DELIMITER = " ";

    /**
     * Called by AWS/
     */
    public SimplePipelineLambdaFunction() {
        super(new CodePipelineCommunicator());
    }

    protected SimplePipelineLambdaFunction(CodePipelineCommunicator communicator) {
        super(communicator);
    }

    @Override
    protected SampleOutputObject processInput(DeployEvent inputObject, String inputRequest, Context context) {
        String inputObjectString = Optional.ofNullable(inputObject).map(Object::toString)
            .orElse(NULL_INPUT_OBJECT);
        String inputRequestString = Optional.ofNullable(inputRequest).orElse(NULL_INPUT_REQUEST);

        String message = String.join(DELIMITER, inputObjectString, inputRequestString);
        SampleOutputObject output = new SampleOutputObject();
        output.setMessage(message);
        logger.debug(output.getMessage());
        return output;
    }
}
