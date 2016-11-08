package io.mobitech.trends.demo.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.mobitech.trends.demo.BuildConfig;
import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.TrendsDemoApplication;

/**
 * Created by Viacheslav Titov on 08.11.2016.
 */

public class HtmlKeywordsFragment extends Fragment {

    public static final String TAG = HtmlKeywordsFragment.class.getSimpleName();

    private static final String SEARCH_TEXT = "SEARCH_TEXT";
    private static final String BASE_URL = "http://api-client.mobitech-search.xyz/?p_key=%s&user_id=%s&q=%s";

    private WebView mWebView;

    private String mKeyword;

    public static HtmlKeywordsFragment newInstance(String keyword) {
        HtmlKeywordsFragment fragment = new HtmlKeywordsFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_TEXT, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;
        mKeyword = getArguments().getString(SEARCH_TEXT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keywords_html, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = (WebView) view.findViewById(R.id.htmlSearchWebView);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
    }

    private void updateData() {
        String url = assembleUrl(mKeyword);
        prepareWebView(url);
        mWebView.loadUrl(url);
    }

    private String assembleUrl(String searchText) {
        try {
            searchText = URLEncoder.encode(searchText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            searchText = URLEncoder.encode(searchText);
        }
        String url = String.format(BASE_URL, TrendsDemoApplication.getInstance().getApiKey(),
                TrendsDemoApplication.getInstance().getUserId(), searchText);
        return BuildConfig.DEBUG ? url + "&test=test" : url;
    }

    private void prepareWebView(String url) {
        mWebView.setWebViewClient(new WebViewClientImpl(url));
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
    }

    private class WebViewClientImpl extends WebViewClient {

        private String myUrl = "";

        public WebViewClientImpl(String url) {
            this.myUrl = url;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            if (MailTo.isMailTo(url)) {
                final MailTo mt = MailTo.parse(url);
                final Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                //i.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                i.putExtra(Intent.EXTRA_CC, mt.getCc());
                i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
                startActivity(Intent.createChooser(i, "Send email"));
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
            } else {
                view.loadUrl(this.myUrl);
            }

            return false;
        }
    }

}
