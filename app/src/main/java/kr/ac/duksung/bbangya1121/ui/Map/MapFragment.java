package kr.ac.duksung.bbangya1121.ui.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import kr.ac.duksung.bbangya1121.R;
import kr.ac.duksung.bbangya1121.databinding.MapFragmentBinding;
import kr.ac.duksung.bbangya1121.ui.gallery.GalleryViewModel;

public class MapFragment extends Fragment {
    private MapFragmentBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel slideshowViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = MapFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}