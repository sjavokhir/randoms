package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.*
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.UIState
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.RandomModel
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onFailure
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onLoading
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.onSuccess
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.databinding.ActivityRandomBinding
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.navigation.NavArguments
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.viewmodels.RandomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRandomBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RandomViewModel>()

    private var mRewardedAd: RewardedAd? = null

    private var randomText: String = ""
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adsInitialize()

        with(binding) {
            toolbar.title = NavArguments.model?.title.orEmpty()
            toolbar.setNavigationOnClickListener { finish() }

            buttonRefresh.onClick {
                randomText = ""
                viewModel.getRandom()
                showRewardedAd()
            }
            buttonShare.onClick { if (randomText.isNotEmpty()) shareText(randomText) }
        }

        viewModel.random.observe(this) { onRandom(it) }

        loadBannerAd()
        loadRewardedAd()
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

    private fun onRandom(uiState: UIState<RandomModel>) {
        with(binding) {
            progressBar.beGone()
            textTitle.beGone()
            textSubtitle.beGone()

            uiState onLoading {
                progressBar.beVisible()
            } onSuccess {
                with(binding) {
                    textTitle.beVisibleIf(!data?.title.isNullOrEmpty())
                    textTitle.text = data?.title.orEmpty()

                    textSubtitle.beVisibleIf(!data?.subtitle.isNullOrEmpty())
                    textSubtitle.text = data?.subtitle.orEmpty()

                    randomText = "${data?.title.orEmpty()}\n\n${data?.subtitle.orEmpty()}"
                }
            } onFailure {
                textTitle.text = message
                textTitle.beVisible()

                randomText = ""
            }
        }
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-9612143868526251/2354227255",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                mRewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
    }

    private fun showRewardedAd() {
        if (counter == 5) {
            counter = 0
            mRewardedAd?.show(this) {}
        }

        counter += 1
    }
}