package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.*
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.helpers.DownloaderManager
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.UIState
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.RandomModel
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onFailure
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onLoading
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onSuccess
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.databinding.ActivityPictureBinding
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.navigation.NavArguments
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.viewmodels.RandomViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PictureActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPictureBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RandomViewModel>()

    @Inject
    lateinit var downloaderManager: DownloaderManager

    private var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adsInitialize()

        with(binding) {
            toolbar.title = NavArguments.model?.title.orEmpty()
            toolbar.setNavigationOnClickListener { finish() }

            buttonRefresh.onClick {
                downloadUrl = ""
                viewModel.getRandom()
            }
            buttonDownload.onClick {
                if (!downloadUrl.isNullOrEmpty()) downloaderManager.download(downloadUrl)
            }
        }

        viewModel.random.observe(this) { onRandomImage(it) }

        loadBannerAd()
    }

    public override fun onPause() {
        binding.adView.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    public override fun onDestroy() {
        binding.adView.destroy()
        super.onDestroy()
    }

    private fun onRandomImage(uiState: UIState<RandomModel>) {
        with(binding) {
            progressBar.beGone()
            imageRandom.beGone()

            uiState onLoading {
                progressBar.beVisible()
            } onSuccess {
                downloadUrl = data?.imageUrl

                imageRandom.loadImage(
                    url = data?.imageUrl,
                    skipMemoryCache = NavArguments.model?.isImageUrl ?: false
                )
                imageRandom.beVisible()
            } onFailure {
                toast(message)
            }
        }
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}