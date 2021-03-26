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

package org.gecko.selenium.driver.firefox;

import org.gecko.selenium.SeleniumDriver;
import org.gecko.selenium.Util;
import org.gecko.selenium.driver.SeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

@SeleniumDriver("firefox")
@Designate(factory = false, ocd = SeleniumConfig.class)
@Component(service = { WebDriver.class, RemoteWebDriver.class, FirefoxDriver.class }, scope = ServiceScope.PROTOTYPE)
public class OSGiFirefoxDriver extends FirefoxDriver {

	@Activate
	public OSGiFirefoxDriver(SeleniumConfig config) {
		super(toDriverService(config), toOptions(config));

	}

	private static FirefoxOptions toOptions(SeleniumConfig config) {
		FirefoxOptions options = new FirefoxOptions();
		// Chrome
		if (config.logLevel() != null) {
			options.setLogLevel(config.logLevel());
		}
		// Chromium
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
		return options;
	}

	public static GeckoDriverService toDriverService(SeleniumConfig config) {

		GeckoDriverService.Builder builder = new GeckoDriverService.Builder();

		builder = Util.appandBuilder(config, builder);

		return builder.build();

	}

}
