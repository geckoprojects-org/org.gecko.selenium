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

package org.gecko.selenium.driver.chrome;

import org.gecko.selenium.SeleniumDriver;
import org.gecko.selenium.Util;
import org.gecko.selenium.driver.SeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

@SeleniumDriver("chrome")
@Designate(factory = false, ocd = SeleniumConfig.class)
@Component(service = { WebDriver.class, RemoteWebDriver.class, ChromiumDriver.class,
		ChromeDriver.class }, scope = ServiceScope.PROTOTYPE)
public class OSGiChromeDriver extends ChromeDriver {

	@Activate
	public OSGiChromeDriver(SeleniumConfig config) {
		super(toDriverService(config), toOptions(config));

	}

	private static ChromeOptions toOptions(SeleniumConfig config) {
		ChromeOptions options = new ChromeOptions();

		if (config.arguments() != null) {
			options.addArguments(config.arguments());
		}
		if (config.binary() != null) {
			options.setBinary(config.binary());
		}
		options.setHeadless(config.headless());
		// AbstractDriverOptions
		options.setAcceptInsecureCerts(config.acceptInsecureCerts());
		if (config.pageLoadStrategy() != null) {
			options.setPageLoadStrategy(config.pageLoadStrategy());
		}
		options.setProxy(Util.toProxy(config));
		options.setStrictFileInteractability(config.strictFileInteractability());
		if (config.unhandledPromptBehaviour() != null) {
			options.setUnhandledPromptBehaviour(config.unhandledPromptBehaviour());
		}
		if (config.binary() != null) {
			options.setBinary(config.binary());
		}
		// Mutable
		Util.toList(config.capabilities()).stream().map(Util::split)
				.forEach(arr -> options.setCapability(arr[0], arr.length == 0 ? null : arr[1]));

		return options;
	}

	public static ChromeDriverService toDriverService(SeleniumConfig config) {

		ChromeDriverService.Builder builder = new ChromeDriverService.Builder();

		builder = Util.appandBuilder(config, builder);

		return builder.build();

	}

}