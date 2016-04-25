// Generated code from Butter Knife. Do not modify!
package co.ipb.adukerang.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LaporActivity$$ViewInjector<T extends co.ipb.adukerang.activity.LaporActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131755207, "field 'tv_id_ruang'");
    target.tv_id_ruang = finder.castView(view, 2131755207, "field 'tv_id_ruang'");
    view = finder.findRequiredView(source, 2131755208, "field 'spBarang'");
    target.spBarang = finder.castView(view, 2131755208, "field 'spBarang'");
    view = finder.findRequiredView(source, 2131755159, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131755159, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131755212, "field 'bChoose'");
    target.bChoose = finder.castView(view, 2131755212, "field 'bChoose'");
    view = finder.findRequiredView(source, 2131755211, "field 'bCamera'");
    target.bCamera = finder.castView(view, 2131755211, "field 'bCamera'");
    view = finder.findRequiredView(source, 2131755209, "field 'txtKeluhan'");
    target.txtKeluhan = finder.castView(view, 2131755209, "field 'txtKeluhan'");
    view = finder.findRequiredView(source, 2131755193, "field 'bLapor'");
    target.bLapor = finder.castView(view, 2131755193, "field 'bLapor'");
    view = finder.findRequiredView(source, 2131755194, "field 'bCancel'");
    target.bCancel = finder.castView(view, 2131755194, "field 'bCancel'");
    view = finder.findRequiredView(source, 2131755213, "field 'foto'");
    target.foto = finder.castView(view, 2131755213, "field 'foto'");
  }

  @Override public void reset(T target) {
    target.tv_id_ruang = null;
    target.spBarang = null;
    target.toolbar = null;
    target.bChoose = null;
    target.bCamera = null;
    target.txtKeluhan = null;
    target.bLapor = null;
    target.bCancel = null;
    target.foto = null;
  }
}
