package com.news.app;

import static com.news.app.Extensions.getRandomItems;
import static com.news.app.Extensions.hideProgress;
import static com.news.app.Extensions.isConnect;
import static com.news.app.Extensions.showProgress;
import static com.news.app.Extensions.showToast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.news.app.databinding.FragmentNewsListBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private FragmentNewsListBinding binding;
    private NavController navController;
    private MainViewModel mainViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNewsList();
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(view);
    }

    private void getNewsList() {
        if (isConnect(requireContext())) {
            showProgress(requireContext());
            Call<NewsDataModel> call = RetrofitClient.getInstance().getMyApi().getNewsList("tesla", "publishedAt", "793012bfdf64450691367744d64bc917");
            call.enqueue(new Callback<NewsDataModel>() {
                @Override
                public void onResponse(@NonNull Call<NewsDataModel> call, @NonNull Response<NewsDataModel> response) {
                    hideProgress();
                    if (response.code() == 200) {
                        assert response.body() != null;
                        binding.topStoryRecyclerVIew.setAdapter(new TopStoryAdapter(requireContext(), getRandomItems(response.body().getArticles(), 20), navController, response.body(), mainViewModel));
                        binding.newsRecyclerView.setAdapter(new NewsAdapter(requireContext(), getRandomItems(response.body().getArticles(), 20), navController, response.body(), mainViewModel));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewsDataModel> call, @NonNull Throwable t) {
                    hideProgress();
                    showToast(requireContext(), t.getMessage());
                }
            });
        }
    }
}