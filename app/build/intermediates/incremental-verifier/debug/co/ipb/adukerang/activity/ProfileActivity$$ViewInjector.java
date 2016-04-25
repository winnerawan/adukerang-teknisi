// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ProfileActivity$$ViewInjector<T extends co.ipb.adukerang.activity.ProfileActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755247, "field 'tv_username'");
    target.tv_username = finder.castView(view, 2131755247, "field 'tv_username'");
    view = finder.findRequiredView(source, 2131755214, "field 'tv_email'");
    target.tv_email = finder.castView(view, 2131755214, "field 'tv_email'");
    view = finder.findRequiredView(source, 2131755246, "field 'avatar'");
    target.avatar = finder.castView(view, 2131755246, "field 'avatar'");
  }

  @Override public void reset(T target) {
    target.tv_username = null;
    target.tv_email = null;
    target.avatar = null;
  }
}
