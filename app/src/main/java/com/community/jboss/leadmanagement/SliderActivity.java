package com.community.jboss.leadmanagement;

import android.os.Bundle;

import com.community.jboss.leadmanagement.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class SliderActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        addSlide(new SimpleSlide.Builder()
                .image(R.drawable.ic_hello)
                .background(R.color.slide_0_background)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(R.string.slide_1_header)
                .description(R.string.slide_1_content)
                .image(R.drawable.leadmanagementslide_1)
                .background(R.color.slide_1_background)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.slide_2_header)
                .description(R.string.slide_2_content)
                .image(R.drawable.ic_client)
                .background(R.color.slide_2_background)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.slide_3_header)
                .description(R.string.slide_3_content)
                .image(R.drawable.ic_micro)
                .background(R.color.slide_3_background)
                .scrollable(false)
                .build());
        setButtonBackVisible(false);

    }
}
