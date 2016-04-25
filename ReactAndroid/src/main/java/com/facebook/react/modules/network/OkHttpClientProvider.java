/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.react.modules.network;

import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.TlsVersion;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

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

    OkHttpClient client = new OkHttpClient();

    try {
      client.setSslSocketFactory(new TLSSocketFactory());
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    // No timeouts by default
    client.setConnectTimeout(0, TimeUnit.MILLISECONDS);
    client.setReadTimeout(0, TimeUnit.MILLISECONDS);
    client.setWriteTimeout(0, TimeUnit.MILLISECONDS);

    return client;
  }
}
