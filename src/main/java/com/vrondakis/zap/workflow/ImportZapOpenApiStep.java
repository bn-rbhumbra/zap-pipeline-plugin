package com.vrondakis.zap.workflow;

import javax.annotation.CheckForNull;

import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class ImportZapOpenApiStep extends Step {
    private final ImportZapOpenApiStepParameters importZapOpenApiStepParameters;

    @DataBoundConstructor
    public ImportZapOpenApiStep(@CheckForNull String path) {
        this.importZapOpenApiStepParameters = new ImportZapOpenApiStepParameters(path);
    }

    @Override
    public StepExecution start(StepContext context) {
        return new ImportZapOpenApiExecution(context, importZapOpenApiStepParameters);
    }

    @Extension
    public static class DescriptorImpl extends DefaultStepDescriptorImpl<ImportZapOpenApiExecution> {
        public DescriptorImpl() {
            super(ImportZapOpenApiExecution.class, "importZapOpenApi", "Load an OpenAPI specification for ZAP to use, from the specified path");
        }
    }
}