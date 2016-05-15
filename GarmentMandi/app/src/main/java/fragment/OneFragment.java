package fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.garmentbazaarb2b.garmentmandi.R;


public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_one, container, false);
/*
        GalleryImageAdapter galImageAdapter = new GalleryImageAdapter(getActivity());
Gallery gallery=(Gallery)root.findViewById(R.id.gallery);
        gallery.setAdapter(galImageAdapter);

*/

        return root ;
    }
    public class GalleryImageAdapter extends BaseAdapter {

        private Activity context;

        private  ImageView imageView;

//        private List<Drawable> plotsImages;

        private  ViewHolder holder;

        public GalleryImageAdapter(Activity context) {

            this.context = context;
//            this.plotsImages = plotsImages;

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new ViewHolder();

                imageView = new ImageView(this.context);

                imageView.setPadding(3, 3, 3, 3);

                convertView = imageView;

                holder.imageView = imageView;

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.imageView.setBackgroundResource(R.drawable.logo);

            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageView.setLayoutParams(new Gallery.LayoutParams(150, 90));

            return imageView;
        }

        private  class ViewHolder {
            ImageView imageView;
        }

    }
}

