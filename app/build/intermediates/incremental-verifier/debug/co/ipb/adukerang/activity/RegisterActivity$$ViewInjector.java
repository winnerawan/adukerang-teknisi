// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RegisterActivity$$ViewInjector<T extends co.ipb.adukerang.activity.RegisterActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755228, "field 'txtUsername'");
    target.txtUsername = finder.castView(view, 2131755228, "field 'txtUsername'");
    view = finder.findRequiredView(source, 2131755229, "field 'txtEmail'");
    target.txtEmail = finder.castView(view, 2131755229, "field 'txtEmail'");
    view = finder.findRequiredView(source, 2131755230, "field 'txtPassword'");
    target.txtPassword = finder.castView(view, 2131755230, "field 'txtPassword'");
    view = finder.findRequiredView(source, 2131755231, "field 'bRegister'");
    target.bRegister = finder.castView(view, 2131755231, "field 'bRegister'");
    view = finder.findRequiredView(source, 2131755188, "field 'txtregid'");
    target.txtregid = finder.castView(view, 2131755188, "field 'txtregid'");
  }

  @Override public void reset(T target) {
    target.txtUsername = null;
    target.txtEmail = null;
    target.txtPassword = null;
    target.bRegister = null;
    target.txtregid = null;
  }
}
