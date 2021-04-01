package com.shailesh.mybusappdagger2.ui.profile;

import com.shailesh.mybusappdagger2.model.Areas;
import com.shailesh.mybusappdagger2.model.Routes;
import com.shailesh.mybusappdagger2.model.Villages;

public interface Interface_Route
{
    public void selectRoute(Routes routes);

    public void selectVillage(Villages villages);

    public void selectArea(Areas areas);
}
