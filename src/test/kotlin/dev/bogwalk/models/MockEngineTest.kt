package dev.bogwalk.models

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class MockEngineTest {
    companion object {
        private lateinit var singlePhotoData: String
        private lateinit var photoListData: String

        @JvmStatic
        @BeforeClass
        fun setup() {
            singlePhotoData = getTestData("src/test/resources/singlePhoto.json")
            photoListData = getTestData("src/test/resources/photoList.json")
        }
    }

    @Test
    fun `image alt is custom if description is missing`() = runBlocking {
        val mockEngine = createMockEngine(singlePhotoData)
        val apiClient = UnsplashClient(mockEngine)
        val photo = apiClient.fetchRandomBird()

        assertEquals("Dwu85P9SOIk", photo.id)
        assertEquals(
            "https://unsplash.com/@exampleuser?utm_source=random_bird&utm_medium=referral",
            photo.user.attribution
        )
        assertEquals("Unsplash image of a bird by Joe Example", photo.alt)
    }

    @Test
    fun `image alt is shortened if description is too long`() = runBlocking {
        val mockEngine = createMockEngine(photoListData)
        val apiClient = UnsplashClient(mockEngine)
        val photo = apiClient.fetchRecentBirds().first()

        assertEquals("BN2af1sg23zdzs", photo.id)
        assertEquals("Big brown bear. Big paws. Cute", photo.alt)
    }

    @Test
    fun `fetchRecentBirds() sorts response by likes then by creation date`() = runBlocking {
        val mockEngine = createMockEngine(photoListData)
        val apiClient = UnsplashClient(mockEngine)
        val photos = apiClient.fetchRecentBirds()

        // 3rd photo shares number of likes with other photos but is included because
        // its position in the original list sorted by date is highest (most recent)
        val expectedIDs = listOf("BN2af1sg23zdzs", "a1f51SG35cvb", "amns45H12Sx")

        assertContentEquals(expectedIDs, photos.map(UnsplashPhoto::id))
    }
}

private fun getTestData(filepath: String): String = File(filepath).readText()

private fun createMockEngine(jsonData: String) = MockEngine {
    respond(
        content = ByteReadChannel(jsonData),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}