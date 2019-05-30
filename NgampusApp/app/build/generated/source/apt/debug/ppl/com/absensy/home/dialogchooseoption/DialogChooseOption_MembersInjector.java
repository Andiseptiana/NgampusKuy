// Generated by Dagger (https://google.github.io/dagger).
package ppl.com.absensy.home.dialogchooseoption;

import dagger.MembersInjector;
import javax.inject.Provider;

public final class DialogChooseOption_MembersInjector
    implements MembersInjector<DialogChooseOption> {
  private final Provider<RecyclerViewOptionsAdapter> recyclerViewOptionsAdapterProvider;

  private final Provider<BottomLineRecyclerViewDecoration> bottomLineRecyclerViewDecorationProvider;

  public DialogChooseOption_MembersInjector(
      Provider<RecyclerViewOptionsAdapter> recyclerViewOptionsAdapterProvider,
      Provider<BottomLineRecyclerViewDecoration> bottomLineRecyclerViewDecorationProvider) {
    this.recyclerViewOptionsAdapterProvider = recyclerViewOptionsAdapterProvider;
    this.bottomLineRecyclerViewDecorationProvider = bottomLineRecyclerViewDecorationProvider;
  }

  public static MembersInjector<DialogChooseOption> create(
      Provider<RecyclerViewOptionsAdapter> recyclerViewOptionsAdapterProvider,
      Provider<BottomLineRecyclerViewDecoration> bottomLineRecyclerViewDecorationProvider) {
    return new DialogChooseOption_MembersInjector(
        recyclerViewOptionsAdapterProvider, bottomLineRecyclerViewDecorationProvider);
  }

  @Override
  public void injectMembers(DialogChooseOption instance) {
    injectRecyclerViewOptionsAdapter(instance, recyclerViewOptionsAdapterProvider.get());
    injectBottomLineRecyclerViewDecoration(
        instance, bottomLineRecyclerViewDecorationProvider.get());
  }

  public static void injectRecyclerViewOptionsAdapter(
      DialogChooseOption instance, RecyclerViewOptionsAdapter recyclerViewOptionsAdapter) {
    instance.recyclerViewOptionsAdapter = recyclerViewOptionsAdapter;
  }

  public static void injectBottomLineRecyclerViewDecoration(
      DialogChooseOption instance,
      BottomLineRecyclerViewDecoration bottomLineRecyclerViewDecoration) {
    instance.bottomLineRecyclerViewDecoration = bottomLineRecyclerViewDecoration;
  }
}