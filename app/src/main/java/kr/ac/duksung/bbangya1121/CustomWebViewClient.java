package kr.ac.duksung.bbangya1121;


import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String url = request.getUrl().toString();

            // Check the URL or resource type you want to preload
            if (url.endsWith(".css")) {
                // Preload CSS files
                // You can add more resource type checks and preloading logic here
                // For example, you can check for image files (e.g., ".jpg", ".png") and preload them.

                // To preload, you can use HttpURLConnection or other methods to fetch the resource.
                // Then, you can create a WebResourceResponse with the preloaded data.
                // For simplicity, let's return null for now.
                return null;
            }
        }

        // Return null for resources you don't want to preload
        return null;
    }
}
