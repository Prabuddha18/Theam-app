package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("DEKU Theme Studio", appName)
  }

  @Test
  fun `test theme repository persistence`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = com.example.data.repository.ThemePreferencesRepository(context)
    assertEquals(com.example.data.model.ThemePresetId.DARK_MONO, repo.preferencesState.value.themePresetId)
    repo.saveThemePreset(com.example.data.model.ThemePresetId.LIGHT_MONO)
    assertEquals(com.example.data.model.ThemePresetId.LIGHT_MONO, repo.preferencesState.value.themePresetId)
  }
}
