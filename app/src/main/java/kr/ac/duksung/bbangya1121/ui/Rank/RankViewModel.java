package kr.ac.duksung.bbangya1121.ui.Rank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RankViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}