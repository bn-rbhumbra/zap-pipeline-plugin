package com.vrondakis.zap.workflow;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.vrondakis.zap.ZapDriver;
import com.vrondakis.zap.ZapDriverController;
import com.vrondakis.zap.ZapExecutionException;
import hudson.model.Result;
import org.jenkinsci.plugins.workflow.steps.StepContext;

public class ImportZapOpenApiExecution extends DefaultStepExecutionImpl {
    private ImportZapOpenApiStepParameters importZapOpenApiStepParameters;

    public ImportZapOpenApiExecution(StepContext context, ImportZapOpenApiStepParameters importZapOpenApiStepParameters) {
        super(context);
        this.importZapOpenApiStepParameters = importZapOpenApiStepParameters;
    }

    @Override
    public boolean start() {
        listener.getLogger().println("zap: Importing OpenAPI specification...");

        if (importZapOpenApiStepParameters == null || importZapOpenApiStepParameters.getPath().isEmpty()) {
            getContext().onFailure(new ZapExecutionException("Could not load OpenAPI file.", listener.getLogger()));
            return false;
        }

        ZapDriver zapDriver = ZapDriverController.getZapDriver(this.run, listener.getLogger());

        try {
            zapDriver.importOpenApi(importZapOpenApiStepParameters.getPath());
        } catch (Exception e) {
            getContext().setResult(Result.FAILURE);
            getContext().onFailure(new ZapExecutionException("Failed to load OpenAPI specification at " + importZapOpenApiStepParameters.getPath(), e, listener.getLogger()));
            return false;
        }

        getContext().onSuccess(true);
        return true;
    }

    // findbugs fails without this because "non-transient non-serializable instance field in serializable class"
    private void writeObject(ObjectOutputStream out) {
    }

    private void readObject(ObjectInputStream in) {
    }
}
