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

package org.gecko.selenium.driver;

import org.gecko.selenium.driver.firefox.ProxyAutoDetect;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public @interface SeleniumConfig {

	@AttributeDefinition(description = "path to the executable driver, if null it will use the System Property 'webdriver.gecko.driver'  or '"
			+ ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY + "'.")
	String executable();

	@AttributeDefinition(description = "port 0 meany use any free port")
	int port() default 0;

	@AttributeDefinition(description = "timeout in millis")
	int timeout() default 2000;

	@AttributeDefinition(description = "Entry must have the pattern `key=value`")
	String[] environment() default {};

	FirefoxDriverLogLevel logLevel();

	String[] arguments();

	String binary();

	boolean headless() default false;

	// AbstractDriverOptions
	boolean acceptInsecureCerts() default false;

	PageLoadStrategy pageLoadStrategy();

	ProxyAutoDetect proxyAutodetect();

	String proxyFtpProxy();

	String proxyHttpProxy();

	String proxyNoProxy();

	String proxyProxyAutoconfigUrl();

	ProxyType proxyProxyType() default ProxyType.SYSTEM;

	String proxySocksPassword();

	String proxySocksProxy();

	String proxySocksUsername();

	int proxySocksVersion();

	String proxySslProxy();

	boolean strictFileInteractability();

	UnexpectedAlertBehaviour unhandledPromptBehaviour();

	@AttributeDefinition(description = "Entry must have the pattern `key=value`")
	String[] capabilities() default {};

}