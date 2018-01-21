package edu.hneu.studentsportal.feature;


import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;

public enum SiteFeature implements Feature {

    // emails

    @Label("Send profile after profile creation")
    SEND_EMAIL_AFTER_PROFILE_CREATION,

    @Label("Send email after profile modification")
    SEND_EMAIL_AFTER_PROFILE_MODIFICATION,

    // account view

    @EnabledByDefault
    @Label("View schedule")
    VIEW_SCHEDULE,

    @EnabledByDefault
    @Label("View current murks")
    VIEW_CURRENT_MARKS,

    @EnabledByDefault
    @Label("View documents")
    VIEW_DOCUMENTS,

    @EnabledByDefault
    @Label("Send email to the decan")
    SEND_EMAIL_TO_DECAN,

    @EnabledByDefault
    @Label("Load profile in pdf")
    LOAD_PROFILE_PDF;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

    public void toggle() {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(this, !isActive()));
    }
}
