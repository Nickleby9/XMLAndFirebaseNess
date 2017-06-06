package ness.edu.xmlandfirebase;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class YnetDetailFragment extends Fragment {

    WebView webView;
    ProgressBar progressBar;

    public static YnetDetailFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        YnetDetailFragment fragment = new YnetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public YnetDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_ynet_detail, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        final String url = getArguments().getString("url");
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("Ness", url);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(view.VISIBLE);
            }
        });



        return v;
    }

}
