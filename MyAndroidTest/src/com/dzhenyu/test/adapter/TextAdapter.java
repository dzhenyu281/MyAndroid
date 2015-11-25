package com.dzhenyu.test.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dzhenyu.test.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by onlymem on 2015/9/18.
 */
public class TextAdapter extends BaseAdapter implements Filterable {

    public static final int HEIGHT_TYPE_DEFAULT = 0;
    public static final int HEIGHT_TYPE_WRAPCONTENT = 1;
    public static final int HEIGHT_TYPE_DEFAULTSELF = 2;
    public static final int DEFAULT_HEIGHT = 50;

    private List<String> list = new ArrayList<>();
    private Context mContext;
    private FrameLayout.LayoutParams layoutParams;
    private List<String> startList;

    public TextAdapter() {
    }

    public TextAdapter(Context context, List<String> strs) {
        this(context, strs, HEIGHT_TYPE_DEFAULT);
    }

    public TextAdapter(Context context, List<String> strs, int type) {
        this(context, strs, type, 0);
    }

    public TextAdapter(Context context, List<String> strs, int type, int height) {
        this.startList = this.list = strs;
        mContext = context;
        setLayoutParams(type, height);
    }

    private void setLayoutParams(int type, int height) {
        float density = mContext.getResources().getDisplayMetrics().density;
        switch (type) {
            case HEIGHT_TYPE_DEFAULTSELF:
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) (height * density));
                break;
            case HEIGHT_TYPE_WRAPCONTENT:
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                break;
            default:
            case HEIGHT_TYPE_DEFAULT:
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) (DEFAULT_HEIGHT * density));
                break;
        }
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.text_list_item, null);
            viewHolder.titleView = (TextView) convertView.findViewById(R.id.tv_text_list);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_text_list);
            if (layoutParams != null)
                viewHolder.relativeLayout.setLayoutParams(layoutParams);
            convertView.setTag(viewHolder);
        }
        viewHolder.titleView.setText(list.get(position));
        return convertView;
    }

    private Filter filter;

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter();
        }
        return filter;
    }

    class ViewHolder {
        TextView titleView;
        RelativeLayout relativeLayout;
    }


    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> resultList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                resultList = startList;
            } else {
                Iterator<String> iterator = constraint.length() > 1 ? list.iterator() : startList.iterator();
                while (iterator.hasNext()) {
                    String str = iterator.next();
                    if (str.contains(constraint))
                        resultList.add(str);
                }
            }
            results.values = resultList;
            results.count = resultList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (List<String>) results.values;
            if (list.size() > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
