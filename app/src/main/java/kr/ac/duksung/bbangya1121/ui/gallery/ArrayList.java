package kr.ac.duksung.bbangya1121.ui.gallery;



import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;


public class ArrayList extends ListFragment {

    private static String[] NUMBERS = new String[]{"🥖 바게뜨 🥖", "🥐 소금빵 🥐", "🥯 크림빵 🥯", "🍞 식빵 🍞", "🍩 마카롱 🍩", "🍰 케이크 🍰", "🥨 마들렌 🥨", "🧈 휘낭시에 🧈", "🎂 파운드 케이크 🎂", "🍪 쿠키 🍪"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, NUMBERS));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void
    onListItemClick(ListView I, View v, int position, long id) {
        getListView().setItemChecked(position, true);
    }
}