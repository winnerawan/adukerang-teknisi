// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SettingsActivity$$ViewInjector<T extends co.ipb.adukerang.activity.SettingsActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755169, "field 'avatar'");
    target.avatar = finder.castView(view, 2131755169, "field 'avatar'");
    view = finder.findRequiredView(source, 2131755232, "field 'txtUsername'");
    target.txtUsername = finder.castView(view, 2131755232, "field 'txtUsername'");
    view = finder.findRequiredView(source, 2131755233, "field 'txtEmail'");
    target.txtEmail = finder.castView(view, 2131755233, "field 'txtEmail'");
    view = finder.findRequiredView(source, 2131755234, "field 'txtPass0'");
    target.txtPass0 = finder.castView(view, 2131755234, "field 'txtPass0'");
    view = finder.findRequiredView(source, 2131755235, "field 'txtPass1'");
    target.txtPass1 = finder.castView(view, 2131755235, "field 'txtPass1'");
    view = finder.findRequiredView(source, 2131755193, "field 'bLapor'");
    target.bLapor = finder.castView(view, 2131755193, "field 'bLapor'");
    view = finder.findRequiredView(source, 2131755194, "field 'bCancel'");
    target.bCancel = finder.castView(view, 2131755194, "field 'bCancel'");
  }

  @Override public void reset(T target) {
    target.avatar = null;
    target.txtUsername = null;
    target.txtEmail = null;
    target.txtPass0 = null;
    target.txtPass1 = null;
    target.bLapor = null;
    target.bCancel = null;
  }
}
