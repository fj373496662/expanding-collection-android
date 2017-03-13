package com.ramotion.expandingcollection;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ramotion.com.expandingcollection.R;

public abstract class ECPagerAdapter extends PagerAdapter {
    public static final String TAG = "ecview";

    private ECPagerCard activeCard;
    private List<ECCardData> dataset;
    private LayoutInflater inflaterService;

    public ECPagerAdapter(Context applicationContext, List<ECCardData> dataset) {
        this.inflaterService = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataset = dataset;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ECPager pager = (ECPager) container;
        final ECPagerCard pagerCard = (ECPagerCard) inflaterService.inflate(R.layout.ec_pager_card, null);
        final ECPagerView pagerContainer = (ECPagerView) pager.getParent();

        ECPagerCardHead cardHead = pagerCard.getPagerCardContent().getCardHead();
        ECPagerCardBody cardBody = pagerCard.getPagerCardContent().getCardBody();

        cardHead.setHeight(pagerContainer.getCardHeight());
        cardHead.setHeadImageDrawable(dataset.get(position).getHeadBgImageDrawable());

        instantiateCard(inflaterService, cardHead, cardBody, dataset.get(position));

        pager.addView(pagerCard, pagerContainer.getCardWidth(), pagerContainer.getCardHeight());
        return pagerCard;
    }

    public abstract void instantiateCard(LayoutInflater inflaterService, ViewGroup cardHead, ViewGroup cardBody, ECCardData data);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        activeCard = (ECPagerCard) object;
    }

    public ECPagerCard getActiveCard() {
        return activeCard;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public List<ECCardData> getDataset() {
        return dataset;
    }
}
