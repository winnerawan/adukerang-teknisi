// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CommentActivity$$ViewInjector<T extends co.ipb.adukerang.activity.CommentActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755159, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131755159, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131755163, "field 'bComment'");
    target.bComment = finder.castView(view, 2131755163, "field 'bComment'");
    view = finder.findRequiredView(source, 2131755162, "field 'txtComment'");
    target.txtComment = finder.castView(view, 2131755162, "field 'txtComment'");
    view = finder.findRequiredView(source, 2131755164, "field 'temp'");
    target.temp = finder.castView(view, 2131755164, "field 'temp'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.bComment = null;
    target.txtComment = null;
    target.temp = null;
  }
}
