package wiseowl.com.au.reaction.model;

/**
 * Created by anish.patel on 7/11/2016.
 */

public class ApiClient {

    public ApiClient() {
    }
//
//
//    public interface FileUploadService {
//        @Multipart
//        @POST("api/v1/nlp")
//        Call<ResponseBody> upload(@Part("description") RequestBody description,
//                                  @Part MultipartBody.Part file);
//    }
//
//    private void uploadFile(File file) {
//        // create upload service client
//        FileUploadService service = ServiceGenerator.createService(FileUploadService.class, this);
//
//        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//
//        // add another part within the multipart request
//        String descriptionString = "hello, this is description speaking";
//        RequestBody description =
//                RequestBody.create(
//                        MediaType.parse("multipart/form-data"), descriptionString);
//
//        final ProgressDialog dialog = ProgressDialog.show(this, "", "Uploading. Please wait...", true);
//
//        // finally, execute the request
//        Call<ResponseBody> call = service.upload(description, body);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call,
//                                   Response<ResponseBody> response) {
//                Log.v("Upload", "success");
//
//                // TODO : Handle response content
//
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("Upload error:", t.getMessage());
//
//                dialog.dismiss();
//            }
//        });
//    }
//
//    public static class ServiceGenerator {
//
//        public static final String API_BASE_URL = "https://54.66.149.212:8080/";
//
//        private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//
//        private static Retrofit.Builder builder =
//                new Retrofit.Builder()
//                        .baseUrl(API_BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create());
//
//        public static <S> S createService(Class<S> serviceClass, Context c) {
//
//            try {
//                httpClient.sslSocketFactory(getSSLConfig(c).getSocketFactory(), new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Retrofit retrofit = builder.client(getUnsafeOkHttpClient()).build();
//            return retrofit.create(serviceClass);
//        }
//    }
//
//    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
//            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//
//        // Loading CAs from an InputStream
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//
//        Certificate ca;
//        // I'm using Java7. If you used Java6 close it manually with finally.
//        try (InputStream cert = context.getResources().openRawResource(R.raw.server)) {
//            ca = cf.generateCertificate(cert);
//        }
//
//        // Creating a KeyStore containing our trusted CAs
//        String keyStoreType = KeyStore.getDefaultType();
//        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//        // Creating a TrustManager that trusts the CAs in our KeyStore.
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        // Creating an SSLSocketFactory that uses our TrustManager
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);
//
//        return sslContext;
//    }
//
//    private static OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//// set your desired log level
//            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//            builder.sslSocketFactory(sslSocketFactory);
//            builder.readTimeout(2, TimeUnit.MINUTES);
//            builder.connectTimeout(2, TimeUnit.MINUTES);
////            builder.addNetworkInterceptor((Interceptor) Level.ALL);
//            builder.interceptors().add(logging);
//            builder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
//
//            OkHttpClient okHttpClient = builder.build();
//            return okHttpClient;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
