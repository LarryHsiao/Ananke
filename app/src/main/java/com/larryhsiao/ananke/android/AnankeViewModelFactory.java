package com.larryhsiao.ananke.android;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.larryhsiao.ananke.android.alarms.views.AlarmsViewModel;

/**
 * Factory to build ViewModels for Ananke.
 */
public class AnankeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;

    public AnankeViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final Ananke ananke = ((Ananke) context.getApplicationContext());
        if (AlarmsViewModel.class.equals(modelClass)) {
            return (T) new AlarmsViewModel(ananke.getAlarms());
        }
        return super.create(modelClass);
    }
}
