package com.zuluft.giodz.autorendereradaptersample.sortedAutoAdapterSample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zuluft.autoadapter.SortedAutoAdapter;
import com.zuluft.generated.AutoAdapterFactory;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;
import com.zuluft.giodz.autorendereradaptersample.sortedAutoAdapterSample.renderers.FootballerOrderableRenderer;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SortedAutoAdapterSampleActivity
        extends
        AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SortedAutoAdapter mSortedAutoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mSortedAutoAdapter = AutoAdapterFactory.createSortedAutoAdapter();
        mSortedAutoAdapter.clicks(FootballerOrderableRenderer.class)
                .map(itemInfo -> itemInfo.renderer)
                .map(renderer -> renderer.footballerModel)
                .subscribe(footballerModel ->
                        Toast.makeText(this,
                                footballerModel.getName(), Toast.LENGTH_LONG)
                                .show());
        mSortedAutoAdapter.clicks(FootballerOrderableRenderer.class, R.id.ivDelete)
                .map(itemInfo -> itemInfo.position)
                .subscribe(position ->
                        mSortedAutoAdapter.remove(position));
        mSortedAutoAdapter.updateAll(Stream.of(getFootballers())
                .map(FootballerOrderableRenderer::new)
                .collect(Collectors.toList()));
        mRecyclerView.setAdapter(mSortedAutoAdapter);
    }

    private List<FootballerModel> getFootballers() {
        return Arrays.asList(
                new FootballerModel("Luis Suarez", 9, "Barcelona"),
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Ousmane Dembele", 11, "FC Barcelona"),
                new FootballerModel("Harry Kane", 9, "Tottenham Hotspur"),
                new FootballerModel("Dele Alli", 20, "Tottenham Hotspur"),
                new FootballerModel("Alexis Sanchez", 7, "Arsenal")
        );
    }
}
