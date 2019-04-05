package handlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import no.bibsys.aws.lambda.events.CodePipelineEvent;
import no.bibsys.aws.lambda.events.DeployEventBuilder;
import no.bibsys.aws.lambda.handlers.templates.CodePipelineCommunicator;
import no.bibsys.aws.tools.IoUtils;
import no.bibsys.aws.tools.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SimplePipelineLambdaFunctionTest {

    private static final String ID = "id1";
    private static final String RESOURCE_FOLDER = "events";
    private static final String REAL_PIPELINE_EVENT = "mock_codePipeline_event.json";
    private SimplePipelineLambdaFunction function;

    @BeforeEach
    public void init() {
        CodePipelineCommunicator communicator = Mockito.mock(CodePipelineCommunicator.class);
        function = new SimplePipelineLambdaFunction(communicator);
    }

    @Test
    public void processInputShouldReturnTheEventAndTheInputString() throws JsonProcessingException {
        CodePipelineEvent event = new CodePipelineEvent(ID);
        String eventString = JsonUtils.newJsonParser().writeValueAsString(event);

        OutputObject output = function.processInput(event, eventString, null);
        assertThat(output.getMessage(), containsString(eventString));
        assertThat(output.getMessage(), containsString(event.toString()));
    }

    @Test
    public void processInputShouldProcessAnAwsPipelineEvent() throws IOException {
        String pipelineEvent = IoUtils.resourceAsString(Paths.get(RESOURCE_FOLDER, REAL_PIPELINE_EVENT));

        CodePipelineEvent event = (CodePipelineEvent) DeployEventBuilder.create(pipelineEvent);
        OutputObject output = function.processInput(event, pipelineEvent, null);
        assertThat(output.getMessage(), containsString(pipelineEvent));
        assertThat(output.getMessage(), containsString(event.toString()));
    }

    @Test
    public void processInputShouldReturnValidOutputtoHandleRequest() throws IOException {
        String pipelineEvent = IoUtils.resourceAsString(Paths.get(RESOURCE_FOLDER, REAL_PIPELINE_EVENT));
        String resourceId = "aaaaaaaa-aaaa-aaaa-1234-123456789012";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pipelineEvent.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        function.handleRequest(inputStream, outputStream, new MockContext());

        byte[] bytes = outputStream.toByteArray();
        String outputString = new String(bytes);
        assertThat(outputString, containsString(resourceId));
    }
}
