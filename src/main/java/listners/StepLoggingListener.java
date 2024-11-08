package listners;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestCaseFinished;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StepLoggingListener implements ConcurrentEventListener {

    private static final Logger logger = LogManager.getLogger(StepLoggingListener.class);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // Register event handlers
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::onTestStepStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        logger.info("Starting scenario: " + event.getTestCase().getName());
    }

    @BeforeStep
    private void onTestStepStarted(TestStepStarted event) {
        String stepText = event.getTestStep().toString();
        logger.info("Executing step: " + stepText);
    }

    @AfterStep
    private void onTestCaseFinished(TestCaseFinished event) {
        logger.info("Finished scenario: " + event.getTestCase().getName());
    }
}
