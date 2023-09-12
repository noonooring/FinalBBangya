package kr.ac.duksung.bbangya1121;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    private ArrayList<ItemData> itemData = new ArrayList<>();

    public MyRecyclerAdapter(ArrayList<ItemData> itemData) {
        this.itemData = itemData;
    }

    public interface MyRecyclerViewClickListener{
        void onItemClicked(int position);
//        void onTitleClicked(int position);
//        void onContentClicked(int position);
//        void onImageViewClicked(int position);
    }

    private MyRecyclerViewClickListener mListener;

    public void setOnClickListener(MyRecyclerViewClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ItemData item = itemData.get(position);
        holder.title.setText((CharSequence) item.getTitle());
        holder.content.setText((CharSequence) item.getContent());
        holder.image.setImageResource(item.getImage());
        //Intent intent = getIntent();
        if (mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos);

//                    Integer number = intent.getIntExtra("image", 0);
//                    String link = intent.getStringExtra("links");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    //startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            image = itemView.findViewById(R.id.image);

            //이미지뷰 원형으로 표시
            image.setBackground(new ShapeDrawable(new OvalShape()));
            image.setClipToOutline(true);
        }
    }

}