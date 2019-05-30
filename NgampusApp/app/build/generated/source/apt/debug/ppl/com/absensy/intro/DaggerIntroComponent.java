// Generated by Dagger (https://google.github.io/dagger).
package ppl.com.absensy.intro;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.repository.SharedPreferencesManager;

public final class DaggerIntroComponent implements IntroComponent {
  private ppl_com_absensy_app_AbsensyAppComponent_settingSharedPreferences
      settingSharedPreferencesProvider;

  private Provider<IntroContract.Presenter> providesIntroPresenterProvider;

  private DaggerIntroComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.settingSharedPreferencesProvider =
        new ppl_com_absensy_app_AbsensyAppComponent_settingSharedPreferences(
            builder.absensyAppComponent);
    this.providesIntroPresenterProvider =
        DoubleCheck.provider(
            IntroModule_ProvidesIntroPresenterFactory.create(
                builder.introModule, settingSharedPreferencesProvider));
  }

  @Override
  public void inject(IntroActivity introActivity) {
    injectIntroActivity(introActivity);
  }

  @CanIgnoreReturnValue
  private IntroActivity injectIntroActivity(IntroActivity instance) {
    IntroActivity_MembersInjector.injectPresenter(instance, providesIntroPresenterProvider.get());
    return instance;
  }

  public static final class Builder {
    private IntroModule introModule;

    private AbsensyAppComponent absensyAppComponent;

    private Builder() {}

    public IntroComponent build() {
      if (introModule == null) {
        throw new IllegalStateException(IntroModule.class.getCanonicalName() + " must be set");
      }
      if (absensyAppComponent == null) {
        throw new IllegalStateException(
            AbsensyAppComponent.class.getCanonicalName() + " must be set");
      }
      return new DaggerIntroComponent(this);
    }

    public Builder introModule(IntroModule introModule) {
      this.introModule = Preconditions.checkNotNull(introModule);
      return this;
    }

    public Builder absensyAppComponent(AbsensyAppComponent absensyAppComponent) {
      this.absensyAppComponent = Preconditions.checkNotNull(absensyAppComponent);
      return this;
    }
  }

  private static class ppl_com_absensy_app_AbsensyAppComponent_settingSharedPreferences
      implements Provider<SharedPreferencesManager> {
    private final AbsensyAppComponent absensyAppComponent;

    ppl_com_absensy_app_AbsensyAppComponent_settingSharedPreferences(
        AbsensyAppComponent absensyAppComponent) {
      this.absensyAppComponent = absensyAppComponent;
    }

    @Override
    public SharedPreferencesManager get() {
      return Preconditions.checkNotNull(
          absensyAppComponent.settingSharedPreferences(),
          "Cannot return null from a non-@Nullable component method");
    }
  }
}
