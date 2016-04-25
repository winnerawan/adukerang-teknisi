// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class DetailsActivity$$ViewInjector<T extends co.ipb.adukerang.activity.DetailsActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755159, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131755159, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131755169, "field 'avatar'");
    target.avatar = finder.castView(view, 2131755169, "field 'avatar'");
    view = finder.findRequiredView(source, 2131755171, "field 'txtPelapor'");
    target.txtPelapor = finder.castView(view, 2131755171, "field 'txtPelapor'");
    view = finder.findRequiredView(source, 2131755172, "field 'txtRuangan'");
    target.txtRuangan = finder.castView(view, 2131755172, "field 'txtRuangan'");
    view = finder.findRequiredView(source, 2131755173, "field 'txtCreated'");
    target.txtCreated = finder.castView(view, 2131755173, "field 'txtCreated'");
    view = finder.findRequiredView(source, 2131755176, "field 'ivKeluhan'");
    target.ivKeluhan = finder.castView(view, 2131755176, "field 'ivKeluhan'");
    view = finder.findRequiredView(source, 2131755177, "field 'txtKeluhan'");
    target.txtKeluhan = finder.castView(view, 2131755177, "field 'txtKeluhan'");
    view = finder.findRequiredView(source, 2131755178, "field 'txtLike'");
    target.txtLike = finder.castView(view, 2131755178, "field 'txtLike'");
    view = finder.findRequiredView(source, 2131755179, "field 'bLike'");
    target.bLike = finder.castView(view, 2131755179, "field 'bLike'");
    view = finder.findRequiredView(source, 2131755180, "field 'bComment'");
    target.bComment = finder.castView(view, 2131755180, "field 'bComment'");
    view = finder.findRequiredView(source, 2131755181, "field 'temp'");
    target.temp = finder.castView(view, 2131755181, "field 'temp'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.avatar = null;
    target.txtPelapor = null;
    target.txtRuangan = null;
    target.txtCreated = null;
    target.ivKeluhan = null;
    target.txtKeluhan = null;
    target.txtLike = null;
    target.bLike = null;
    target.bComment = null;
    target.temp = null;
  }
}
