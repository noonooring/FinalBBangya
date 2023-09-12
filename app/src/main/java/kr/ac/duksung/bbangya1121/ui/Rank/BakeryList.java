package kr.ac.duksung.bbangya1121.ui.Rank;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

public class BakeryList extends ListFragment {

    private static String[] NUMBERS = new String[]{"Cafe de 220VOLT", "프라리네", "성심당", "히포파운드", "베어스덴베이커리", "씨엘비베이커리", "이복근베이커리",
            "콜마르브레드", "푸하하크림빵", "바게트 제작소", "Pelican Bakery", "SF bagels", "베이커리 씨어터", "맘모스 베이커리", "안스베이커리 구월 본점", "솜솜베이커리"};
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
