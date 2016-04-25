// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginActivity$$ViewInjector<T extends co.ipb.adukerang.activity.LoginActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755214, "field 'txtEmail'");
    target.txtEmail = finder.castView(view, 2131755214, "field 'txtEmail'");
    view = finder.findRequiredView(source, 2131755215, "field 'txtPassword'");
    target.txtPassword = finder.castView(view, 2131755215, "field 'txtPassword'");
    view = finder.findRequiredView(source, 2131755217, "field 'bLogin'");
    target.bLogin = finder.castView(view, 2131755217, "field 'bLogin'");
    view = finder.findRequiredView(source, 2131755216, "field 'level'");
    target.level = finder.castView(view, 2131755216, "field 'level'");
    view = finder.findRequiredView(source, 2131755182, "field 'snackbarCoordinatorLayout'");
    target.snackbarCoordinatorLayout = finder.castView(view, 2131755182, "field 'snackbarCoordinatorLayout'");
  }

  @Override public void reset(T target) {
    target.txtEmail = null;
    target.txtPassword = null;
    target.bLogin = null;
    target.level = null;
    target.snackbarCoordinatorLayout = null;
  }
}
