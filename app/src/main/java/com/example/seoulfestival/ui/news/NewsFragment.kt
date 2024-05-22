package com.example.seoulfestival.ui.news

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seoulfestival.R
import com.example.seoulfestival.base.BaseFragment
import com.example.seoulfestival.databinding.FragmentNewsBinding
import com.example.seoulfestival.model.NewsItem
import com.example.seoulfestival.service.NaverNewsApi
import com.example.seoulfestival.util.formatDate
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import kotlin.concurrent.thread

class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_news

    private val newsApi = NaverNewsApi()
    private val keywords = listOf(
        "서초문화재단", "롯데콘서트홀", "세종대극장", "세종씨어터",
        "세종체임버홀", "마포아트센터", "양재 aT센터", "코엑스"
    )

    override fun aboutBinding() {
        setupToolbar(
            appLogoVisible = false,
            leftTitleVisible = false,
            toolbarTitleVisible = true,
            toolbarTitleText = getString(R.string.pressRelease),
            toolbarBackClickListener = null
        )

        fetchNews()
    }

    private fun parseNews(response: JSONObject): List<NewsItem> {
        val items = response.getJSONArray("items")
        val newsList = mutableListOf<NewsItem>()
        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            val formattedDate = formatDate(item.getString("pubDate"))

            newsList.add(
                NewsItem(
                    title = cleanHtmlTags(item.getString("title")),
                    link = item.getString("link"),
                    description = cleanHtmlTags(item.getString("description")),
                    pubDate = formattedDate
                )
            )
        }
        return newsList
    }

    private fun cleanHtmlTags(html: String): String {
        return Jsoup.clean(html, "", Whitelist.none(), Document.OutputSettings().prettyPrint(false))
    }

    private fun setupRecyclerView(newsList: List<NewsItem>) {
        viewDataBinding.newsRc.layoutManager = LinearLayoutManager(context)
        viewDataBinding.newsRc.adapter = NewsAdapter(newsList) { newsItem ->
            val bundle = Bundle().apply {
                putString("link", newsItem.link)
            }
            findNavController().navigate(R.id.actionNewsDetailFragment, bundle)
        }
    }

    private fun fetchNews() {
        activity?.runOnUiThread {
            viewDataBinding.progressBar.visibility = View.VISIBLE
        }

        thread {
            val allNewsList = mutableListOf<NewsItem>()
            keywords.forEach { keyword ->
                val response = newsApi.searchNews(keyword)
                response?.let {
                    val newsList = parseNews(it)
                    allNewsList.addAll(newsList)
                }
            }
            activity?.runOnUiThread {
                viewDataBinding.progressBar.visibility = View.GONE
                setupRecyclerView(allNewsList)
            }
        }
    }

    override fun observeData() {
    }
}
