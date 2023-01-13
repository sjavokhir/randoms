package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.R
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.adsInitialize
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.databinding.ActivityMainBinding
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.fragments.*
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.navigation.NavRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
    }

    private var mInterstitialAd: InterstitialAd? = null

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adsInitialize()

        with(binding) {
            setSupportActionBar(toolbar)

            navController.graph = navController.createGraph(
                startDestination = NavRoutes.pictures
            ) {
                fragment<PicturesFragment>(route = NavRoutes.pictures) {
                    label = getString(R.string.title_pictures)
                }
                fragment<JokesFragment>(route = NavRoutes.jokes) {
                    label = getString(R.string.title_memes)
                }
                fragment<FactsFragment>(route = NavRoutes.facts) {
                    label = getString(R.string.title_facts)
                }
                fragment<QuotesFragment>(route = NavRoutes.quotes) {
                    label = getString(R.string.title_quotes)
                }
                fragment<MoreFragment>(route = NavRoutes.more) {
                    label = getString(R.string.title_more)
                }
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.route == NavRoutes.pictures) {
                    bottomNav.selectedItemId = R.id.pictures
                }

                toolbar.title = destination.label.toString()
            }

            setupWithNavControllerCustom()
        }

        loadBannerAd()
        loadInterstitialAd()
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

    private fun loadBannerAd() {
        return
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-9612143868526251/4951366788",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
    }

    private fun showInterstitialAd() {
        if (counter == 5) {
            counter = 0
            mInterstitialAd?.show(this)
        }

        counter += 1
    }

    private fun setupWithNavControllerCustom() {
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .setPopUpTo(
                navController.graph.findStartDestination().id,
                inclusive = false,
                saveState = true
            )
            .build()

        binding.bottomNav.setOnItemSelectedListener { item ->
            showInterstitialAd()

            when (item.itemId) {
                R.id.pictures -> {
                    navController.navigate(NavRoutes.pictures, options)
                    return@setOnItemSelectedListener true
                }
                R.id.memes -> {
                    navController.navigate(NavRoutes.jokes, options)
                    return@setOnItemSelectedListener true
                }
                R.id.facts -> {
                    navController.navigate(NavRoutes.facts, options)
                    return@setOnItemSelectedListener true
                }
                R.id.quotes -> {
                    navController.navigate(NavRoutes.quotes, options)
                    return@setOnItemSelectedListener true
                }
                R.id.more -> {
                    navController.navigate(NavRoutes.more, options)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
