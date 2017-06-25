package edu.hneu.studentsportal.feature;


import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;

public enum SiteFeature implements Feature {

    @Label("Send profile after profile creation")
    SEND_EMAIL_AFTER_PROFILE_CREATION,

    @Label("Send email after profile modification")
    SEND_EMAIL_AFTER_PROFILE_MODIFICATION;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

    public void toggle() {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(this, !isActive()));
    }
}
