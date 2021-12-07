package com.example.flag;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.flag.placeholder.PlaceholderContent;
import com.example.flag.databinding.FragmentItemDetailBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */

public class ItemDetailFragment<waveAnimation> extends Fragment {
    private Animation waveAnimation;
    List<String> idList=new ArrayList<>();
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";


    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private ImageView imageView;   //set the imageView

    private final View.OnDragListener dragListener = (v, event) -> {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        imageView= binding.imageView;

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {        //Match national flags based on countryid
            switch (mItem.id)
            {
                case "1":
                    ((ImageView)imageView.findViewById(R.id.imageView)).setImageResource(R.drawable.country1);
                    break;
                case "2":
                    ((ImageView)imageView.findViewById(R.id.imageView)).setImageResource(R.drawable.country2);
                    break;
                case "3":
                    ((ImageView)imageView.findViewById(R.id.imageView)).setImageResource(R.drawable.country3);
                    break;
                case "4":
                    ((ImageView)imageView.findViewById(R.id.imageView)).setImageResource(R.drawable.country4);
                    break;
                case "5":
                    ((ImageView)imageView.findViewById(R.id.imageView)).setImageResource(R.drawable.country5);
                    break;


            }

                    waveFlag();//Call the animation function



            if (mToolbarLayout != null) {
                mToolbarLayout.setTitle(mItem.countryName);//
            }
        }


    }

    public void waveFlag(){   // the animation function
        waveAnimation = AnimationUtils.loadAnimation(this.imageView.getContext(), R.anim.wave);
        imageView.startAnimation(waveAnimation);
    }

}
