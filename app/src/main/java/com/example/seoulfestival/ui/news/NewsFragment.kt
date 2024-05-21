package com.example.seoulfestival.ui.news

import android.os.Bundle
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
        thread {
            val response = newsApi.searchNews("문화행사")
            response?.let {
                val newsList = parseNews(it)
                activity?.runOnUiThread {
                    setupRecyclerView(newsList)
                }
            }
        }
    }

    override fun observeData() {
    }
}
