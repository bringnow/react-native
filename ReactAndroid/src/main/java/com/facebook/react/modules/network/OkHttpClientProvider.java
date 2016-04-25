/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react.modules.network;

import javax.annotation.Nullable;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.TlsVersion;

/**
 * Helper class that provides the same OkHttpClient instance that will be used for all networking
 * requests.
 */
public class OkHttpClientProvider {

  // Centralized OkHttpClient for all networking requests.
  private static @Nullable OkHttpClient sClient;

  public static OkHttpClient getOkHttpClient() {
    if (sClient == null) {
      sClient = createClient();
    }
    return sClient;
  }

  private static OkHttpClient createClient() {
    // TODO: #7108751 plug in stetho

    ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
      .tlsVersions(TlsVersion.TLS_1_2)
      .cipherSuites(
        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
      .build();

    OkHttpClient client = new OkHttpClient();

    client.setConnectionSpecs(Collections.singletonList(spec));

    // No timeouts by default
    client.setConnectTimeout(0, TimeUnit.MILLISECONDS);
    client.setReadTimeout(0, TimeUnit.MILLISECONDS);
    client.setWriteTimeout(0, TimeUnit.MILLISECONDS);

    return client;
  }
}
