package com.news.app;

import static com.news.app.Extensions.NEWSDATA;
import static com.news.app.Extensions.getRandomItems;
import static com.news.app.Extensions.loadImage;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.news.app.databinding.FragmentNewsDetailBinding;

public class NewsDetailFragment extends Fragment {

    private FragmentNewsDetailBinding binding;
    private NavController navController;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        if (getArguments() != null) {
            Article article = new Gson().fromJson(requireArguments().getString(NEWSDATA), Article.class);
//            NewsDataModel newsDataModel = new Gson().fromJson(requireArguments().getString(NEWSLIST), NewsDataModel.class);
            setUpNewsData(article/*, newsDataModel*/);
        }
    }

    public void setUpNewsData(Article article/*, NewsDataModel newsDataModel*/) {
        loadImage(binding.newsImageView, article.getUrlToImage());
        binding.newsTitleTextView.setText(article.getTitle());
        binding.newsDecsTextView.setText(article.getDescription());
        binding.authorNameTextView.setText(article.getAuthor());
        binding.contentDecsTextView.setText(article.getContent());

        new Handler().postDelayed(() -> mainViewModel.articleData.observe(getViewLifecycleOwner(), data -> {
            binding.relatedStoriesRecyclerView.setAdapter(new RelatedStoriesAdapter(requireContext(), getRandomItems(data.getArticles(), 20), navController, data, mainViewModel));
        }), 50);

    }
}