/*
 * Copyright 2016-2021 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.generic;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.ResourceHarness;
import com.diffplug.spotless.SerializableEqualityTester;

class IndentStepTest extends ResourceHarness {
	@Test
	void tabToTab() throws Throwable {
		FormatterStep indent = IndentStep.Type.TAB.create(4);
		assertOnResources(indent, "indent/IndentedWithTab.test", "indent/IndentedWithTab.test");
	}

	@Test
	void spaceToSpace() throws Throwable {
		FormatterStep indent = IndentStep.Type.SPACE.create(4);
		assertOnResources(indent, "indent/IndentedWithSpace.test", "indent/IndentedWithSpace.test");
	}

	@Test
	void spaceToTab() throws Throwable {
		FormatterStep indent = IndentStep.Type.TAB.create(4);
		assertOnResources(indent, "indent/IndentedWithSpace.test", "indent/IndentedWithTab.test");
	}

	@Test
	void tabToSpace() throws Throwable {
		FormatterStep indent = IndentStep.Type.SPACE.create(4);
		assertOnResources(indent, "indent/IndentedWithTab.test", "indent/IndentedWithSpace.test");
	}

	@Test
	void doesntClipNewlines() throws Throwable {
		FormatterStep indent = IndentStep.Type.SPACE.create(4);
		String blankNewlines = "\n\n\n\n";
		Assertions.assertEquals(blankNewlines, indent.format(blankNewlines, new File("")));
	}

	@Test
	void equality() {
		new SerializableEqualityTester() {
			IndentStep.Type type = IndentStep.Type.SPACE;
			int numSpacesPerTab = 2;

			@Override
			protected void setupTest(API api) {
				api.areDifferentThan();

				numSpacesPerTab = 4;
				api.areDifferentThan();

				type = IndentStep.Type.TAB;
				api.areDifferentThan();

				numSpacesPerTab = 2;
				api.areDifferentThan();
			}

			@Override
			protected FormatterStep create() {
				return type.create(numSpacesPerTab);
			}
		}.testEquals();
	}
}
