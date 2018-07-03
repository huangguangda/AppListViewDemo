package cn.edu.gdmec.android.applistviewdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppListActivity extends AppCompatActivity{

    private ListView appListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        appListView = findViewById(R.id.app_list_view);

        /*List<String> appNames = new ArrayList<>();

        appNames.add("QQ");
        appNames.add("微信");
        appNames.add("简书");*/


        //appListView.setAdapter(new AppListAdapter(getAppInfos()));
        final List<ResolveInfo> appInfos = getAppInfos();
        appListView.setAdapter(new AppListAdapter(appInfos));

        //添加头视图
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View handerView = layoutInflater.inflate(R.layout.header_list_demo,null);
        appListView.addHeaderView(handerView);

        //点击APP事件
        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取包名
                String packageName = appInfos.get(position).activityInfo.packageName;
                //获取应用名
                String className = appInfos.get(position).activityInfo.name;


                //拼接-点击方法
                ComponentName componentName = new ComponentName(packageName,className);
                final Intent intent = new Intent();
                //通过Intent设置组件名
                intent.setComponent(componentName);
                startActivity(intent);

            }
        });

        //长按的效果
        /*appListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });*/


    }

    //获取所有的应用信息
    private List<ResolveInfo> getAppInfos(){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent,0);
    }

    public class AppListAdapter extends BaseAdapter{

        //这就是要填充的数据列表

        List<ResolveInfo> mAppInfos;

        //改掉
        public AppListAdapter(List<ResolveInfo> appInfos){
            mAppInfos=appInfos;
        }


        @Override
        public int getCount() {
            //有多少条数据
            return mAppInfos.size();
        }

        @Override
        public Object getItem(int position) {
            //返回获取当前Position位置的这一条
            return mAppInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            //返回当前position位置的这一条的ID
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //处理 view -- data 填充数据的一个过程

            ViewHolder viewHolder = new ViewHolder();

            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_app_list_view,null);

                viewHolder.mAppIconImageView = convertView.findViewById(R.id.app_icon_image_view);
                viewHolder.mAppNameTextView = convertView.findViewById(R.id.app_name_text_view);
//TextView appNameTextView
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            //appNameTextView appIconImageView

            viewHolder.mAppNameTextView.setText(mAppInfos.get(position).activityInfo.loadLabel(getPackageManager()));
            viewHolder.mAppIconImageView.setImageDrawable(mAppInfos.get(position).activityInfo.loadIcon(getPackageManager()));

                //点击方法convertView
           /* appNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取包名
                    String packageName = mAppInfos.get(position).activityInfo.packageName;
                    //获取应用名
                    String className = mAppInfos.get(position).activityInfo.name;


                    //拼接-点击方法
                    ComponentName componentName = new ComponentName(packageName,className);
                    final Intent intent = new Intent();
                    //通过Intent设置组件名
                    intent.setComponent(componentName);
                    startActivity(intent);
                }
            });*/

            return convertView;
        }

        public class ViewHolder{
            public ImageView mAppIconImageView;
            public TextView mAppNameTextView;
        }




    }
}
