// Generated by Dagger (https://google.github.io/dagger).
package ppl.com.absensy.boot;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.internal.Preconditions;
import ppl.com.absensy.app.AbsensyAppComponent;

public final class DaggerBootServiceComponent implements BootServiceComponent {
  private AbsensyAppComponent absensyAppComponent;

  private DaggerBootServiceComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.absensyAppComponent = builder.absensyAppComponent;
  }

  @Override
  public void inject(BootService bootService) {
    injectBootService(bootService);
  }

  @CanIgnoreReturnValue
  private BootService injectBootService(BootService instance) {
    BootService_MembersInjector.injectSubjectDao(
        instance,
        Preconditions.checkNotNull(
            absensyAppComponent.subjectDao(),
            "Cannot return null from a non-@Nullable component method"));
    BootService_MembersInjector.injectClassReminder(
        instance,
        Preconditions.checkNotNull(
            absensyAppComponent.classReminder(),
            "Cannot return null from a non-@Nullable component method"));
    return instance;
  }

  public static final class Builder {
    private AbsensyAppComponent absensyAppComponent;

    private Builder() {}

    public BootServiceComponent build() {
      if (absensyAppComponent == null) {
        throw new IllegalStateException(
            AbsensyAppComponent.class.getCanonicalName() + " must be set");
      }
      return new DaggerBootServiceComponent(this);
    }

    public Builder absensyAppComponent(AbsensyAppComponent absensyAppComponent) {
      this.absensyAppComponent = Preconditions.checkNotNull(absensyAppComponent);
      return this;
    }
  }
}