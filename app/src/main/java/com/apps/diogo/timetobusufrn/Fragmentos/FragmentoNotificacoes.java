package com.apps.diogo.timetobusufrn.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.diogo.timetobusufrn.R;

/**
 * Created by Diogo on 17/10/2017.
 */

public class FragmentoNotificacoes extends Fragment
{
    Context context;
    
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_notificacoes, container, false);
        return view;
    }
    
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        //Toast.makeText(context, "Pegando Contexto do Pai - Notificacoes" , Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onAttachFragment(Fragment fragment)
    {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);
        
        //Toast.makeText(context, String.valueOf(fragment.getId()), Toast.LENGTH_SHORT).show();
    }
}
