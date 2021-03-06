// Generated by Dagger (https://google.github.io/dagger).
package ppl.com.absensy.home.recyclerviewsubject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class RecyclerViewSubjectModule_ProvidesRecyclerViewSubjectAdapterFactory
    implements Factory<RecyclerViewSubjectAdapter> {
  private final RecyclerViewSubjectModule module;

  public RecyclerViewSubjectModule_ProvidesRecyclerViewSubjectAdapterFactory(
      RecyclerViewSubjectModule module) {
    this.module = module;
  }

  @Override
  public RecyclerViewSubjectAdapter get() {
    return provideInstance(module);
  }

  public static RecyclerViewSubjectAdapter provideInstance(RecyclerViewSubjectModule module) {
    return proxyProvidesRecyclerViewSubjectAdapter(module);
  }

  public static RecyclerViewSubjectModule_ProvidesRecyclerViewSubjectAdapterFactory create(
      RecyclerViewSubjectModule module) {
    return new RecyclerViewSubjectModule_ProvidesRecyclerViewSubjectAdapterFactory(module);
  }

  public static RecyclerViewSubjectAdapter proxyProvidesRecyclerViewSubjectAdapter(
      RecyclerViewSubjectModule instance) {
    return Preconditions.checkNotNull(
        instance.providesRecyclerViewSubjectAdapter(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
