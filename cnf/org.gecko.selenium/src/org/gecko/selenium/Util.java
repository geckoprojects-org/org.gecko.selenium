/*******************************************************************************
 * Copyright (c) 2021 Data In Motion Consulting GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *******************************************************************************/

package org.gecko.selenium;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.gecko.selenium.driver.SeleniumConfig;
import org.gecko.selenium.driver.firefox.ProxyAutoDetect;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.service.DriverService;

public class Util {

	private Util() {
	}
	public static <T extends DriverService.Builder<?,T>> T appandBuilder(SeleniumConfig config, T builder) {
		Map<String, String> envMap = Util.toList(config.environment()).stream().map(Util::split)
				.collect(Collectors.toMap(arr -> arr[0], arr -> arr.length == 1 ? null : arr[0]));
		
		builder = builder.usingPort(config.port());
		builder = builder.withEnvironment(envMap);
		builder = builder.withTimeout(Duration.ofMillis(config.timeout()));

		if (config.executable() != null && !config.executable().isEmpty()) {
			builder = builder.usingDriverExecutable(new File(config.executable()));
		}
		return builder;
	}
	public static Proxy toProxy(SeleniumConfig config) {
		Proxy proxy = new Proxy();

		if (config.proxyAutodetect() != null) {
			proxy.setAutodetect(config.proxyAutodetect() == ProxyAutoDetect.TRUE);
		}
		if (config.proxyFtpProxy() != null) {
			proxy.setFtpProxy(config.proxyFtpProxy());
		}
		if (config.proxyHttpProxy() != null) {
			proxy.setHttpProxy(config.proxyHttpProxy());
		}
		if (config.proxyNoProxy() != null) {
			proxy.setNoProxy(config.proxyNoProxy());
		}
		if (config.proxyProxyAutoconfigUrl() != null) {
			proxy.setProxyAutoconfigUrl(config.proxyProxyAutoconfigUrl());
		}
		if (config.proxyProxyType() != null) {
			proxy.setProxyType(config.proxyProxyType());
		}
		if (config.proxySocksPassword() != null) {
			proxy.setSocksPassword(config.proxySocksPassword());
		}
		if (config.proxySocksProxy() != null) {
			proxy.setSocksProxy(config.proxySocksProxy());
		}
		if (config.proxySocksUsername() != null) {
			proxy.setSocksUsername(config.proxySocksUsername());
		}
		if (config.proxySocksVersion() != 0) {
			proxy.setSocksVersion(config.proxySocksVersion());
		}
		if (config.proxySslProxy() != null) {
			proxy.setSslProxy(config.proxySslProxy());
		}
		return proxy;
	}
	public static List<File> toFiles(String[] strings) {
		if (strings == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(strings).stream().map(File::new).collect(Collectors.toList());
	}

	public static List<String> toList(String[] strings) {

		if (strings == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(strings);
	}

	public static String[] split(final String val) {
		final int pos = val.indexOf('=');
		if (pos == -1) {
			return new String[] { val, "true" };
		}
		return new String[] { val.substring(0, pos), val.substring(pos + 1) };
	}

}
