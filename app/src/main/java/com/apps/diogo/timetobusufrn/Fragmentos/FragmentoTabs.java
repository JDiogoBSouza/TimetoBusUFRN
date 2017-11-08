package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.apps.diogo.timetobusufrn.R;

/**
 * Created by Diogo on 17/10/2017.
 */

public class FragmentoTabs extends Fragment
{
    Context context;
    
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    int mCurrentItem;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        view = inflater.inflate(R.layout.frag_tabs, container, false);
        
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
            
        return view;
    }
    
    private class sliderAdapter extends FragmentPagerAdapter
    {
        final  String tabs[]={"Timeline", "Mapa", "Horarios"};

        public sliderAdapter(FragmentManager fm)
        {
            super(fm);
        }
        
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new TimelineFragment();
                
                case 1:
                    return new MapsFragment();
                
                case 2:
                    return new HorariosFragment();
                
                default:
                    return new TimelineFragment();
            }
        }
        
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position)
        {
            return tabs[position];
        }
    }
    
    @Override
    public void onAttach(Context context)
    {
        this.context = context;
        super.onAttach(context);
        //Toast.makeText(context, "Pegando Contexto do Pai" , Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onAttachFragment(Fragment fragment)
    {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);
        //Toast.makeText(context, String.valueOf(fragment.getId()), Toast.LENGTH_SHORT).show();
    }
}
