package com.example.flag;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flag.databinding.FragmentItemListBinding;
import com.example.flag.databinding.ItemListContentBinding;

import com.example.flag.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {
    private static String []countryId={"false"};
    private static String []countryId2={"false"};
    private String tempId;
    List<String> idList=new ArrayList<>();
    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    public static boolean useSet(String[] arr, String targetValue) {   //Test whether the specified string is included in the string array
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    public  void save(String id){    //Store clicked countries through sharedpreference

        SharedPreferences prefs = getActivity().getPreferences(getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("countryId",id);
        editor.apply();


    }
    public String countryId(){
        SharedPreferences prefs = getActivity().getPreferences(getContext().MODE_PRIVATE);
        return prefs.getString("countryId","false");

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(getContext().MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();           //clear shared Preference
//        editor.clear();
//        editor.apply();

       String conIds= prefs.getString("countryId","false");         //Read the data stored in shared preference
       countryId= conIds.split(",");                                       //countryId is called in onBindViewHolder

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.itemList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        /* Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        View.OnClickListener onClickListener = itemView -> {
            PlaceholderContent.PlaceholderItem item =
                    (PlaceholderContent.PlaceholderItem) itemView.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
            if (itemDetailFragmentContainer != null) {
                Navigation.findNavController(itemDetailFragmentContainer)
                        .navigate(R.id.fragment_item_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
            }

            if(!countryId().equals("false")){
                idList.add(countryId());
            }

            idList.add(item.id);                           //add item id to idList

            String ids = String.join(",",idList);  //Convert list to string

            save(ids);                                      //save String

            tempId= prefs.getString("countryId","false");  //Read the data stored in shared preference
            countryId2= tempId.split(",");                        //split the string by ,


            if(useSet(countryId2, item.id)){                            //Settings that have been clicked disappear

                itemView.setVisibility(View.INVISIBLE);
            }

        };

        /*
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        View.OnContextClickListener onContextClickListener = itemView -> {
            PlaceholderContent.PlaceholderItem item =
                    (PlaceholderContent.PlaceholderItem) itemView.getTag();
            Toast.makeText(
                    itemView.getContext(),
                    "Context click of item " + item.id,
                    Toast.LENGTH_LONG
            ).show();
            return true;
        };

        setupRecyclerView(recyclerView, onClickListener, onContextClickListener);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View.OnClickListener onClickListener,
            View.OnContextClickListener onContextClickListener
    ) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                PlaceholderContent.ITEMS,
                onClickListener,
                onContextClickListener
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PlaceholderContent.PlaceholderItem> mValues;
        private final View.OnClickListener mOnClickListener;
        private final View.OnContextClickListener mOnContextClickListener;


        SimpleItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items,
                                      View.OnClickListener onClickListener,
                                      View.OnContextClickListener onContextClickListener) {
            mValues = items;
            mOnClickListener = onClickListener;
            mOnContextClickListener = onContextClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

    
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (!useSet(countryId, mValues.get(position).id)) {//The previously clicked countries are not initialized
                holder.mIdView.setImageResource(mValues.get(position).countryImage);   //set country flag as image
                holder.mContentView.setText(mValues.get(position).countryName);        //set country name as text     

                holder.itemView.setTag(mValues.get(position));
                holder.itemView.setOnClickListener(mOnClickListener);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.itemView.setOnContextClickListener(mOnContextClickListener);
                }
                holder.itemView.setOnLongClickListener(v -> {
                    // Setting the item id as the clip data so that the drop target is able to
                    // identify the id of the content
                    ClipData.Item clipItem = new ClipData.Item(mValues.get(position).id);
                    ClipData dragData = new ClipData(
                            ((PlaceholderContent.PlaceholderItem) v.getTag()).countryName,
                            new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                            clipItem
                    );

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(
                                dragData,
                                new View.DragShadowBuilder(v),
                                null,
                                0
                        );
                    } else {
                        v.startDrag(
                                dragData,
                                new View.DragShadowBuilder(v),
                                null,
                                0
                        );
                    }
                    return true;
                });
            }
        } 

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mIdView;
            final TextView mContentView;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mIdView = binding.idImage;                          //bind country flag
                mContentView = binding.content;
            }

        }
    }
}