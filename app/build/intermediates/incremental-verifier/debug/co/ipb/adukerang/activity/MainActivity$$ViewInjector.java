// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends co.ipb.adukerang.activity.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755183, "field 'register'");
    target.register = finder.castView(view, 2131755183, "field 'register'");
    view = finder.findRequiredView(source, 2131755184, "field 'login'");
    target.login = finder.castView(view, 2131755184, "field 'login'");
  }

  @Override public void reset(T target) {
    target.register = null;
    target.login = null;
  }
}
