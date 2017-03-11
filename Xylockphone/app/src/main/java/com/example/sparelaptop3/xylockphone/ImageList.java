package com.example.sparelaptop3.xylockphone;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

public class ImageList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] labels;
    private final Integer[] imageId;
    public ImageList(Activity context,
                      String[] labels, Integer[] imageId) {
        super(context, R.layout.image_list, labels);
        this.context = context;
        this.labels = labels;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.image_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(labels[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}