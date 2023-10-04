package edu.flab.util;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class YoutubeVideoIdExtractorTest {
	@ParameterizedTest
	@MethodSource("testParameter")
	void test1(String youtubeUrl, String videoId) {
		assertThat(YoutubeVideoIdExtractor.getVideoId(youtubeUrl)).isEqualTo(videoId);
	}

	private static Stream<Arguments> testParameter() {
		return Stream.of(
			Arguments.of("https://youtube.com/live/eLlxrBmD3H4", "eLlxrBmD3H4"),
			Arguments.of("https://www.youtube.com/live/eLlxrBmD3H4", "eLlxrBmD3H4"),
			Arguments.of("http://www.youtube.com/watch?v=0zM4nApSvMg&feature=feedrec_grec_index", "0zM4nApSvMg"),
			Arguments.of("http://www.youtube.com/user/SomeUser#p/a/u/1/QDK8U-VIH_o", "QDK8U-VIH_o"),
			Arguments.of("http://www.youtube.com/v/0zM4nApSvMg?fs=1&amp;hl=en_US&amp;rel=0", "0zM4nApSvMg"),
			Arguments.of("http://www.youtube.com/embed/0zM4nApSvMg?rel=0", "0zM4nApSvMg"),
			Arguments.of("http://www.youtube.com/watch?v=0zM4nApSvMg", "0zM4nApSvMg"),
			Arguments.of("http://youtu.be/0zM4nApSvMg", "0zM4nApSvMg"),
			Arguments.of("https://www.youtube.com/watch?v=pJegNopBLL8", "pJegNopBLL8"),
			Arguments.of("http://www.youtube.com/watch?v=0zM4nApSvMg#t=0m10s", "0zM4nApSvMg"),
			Arguments.of("https://www.youtube.com/watch?v=0zM4nApSvMg&feature=youtu.be", "0zM4nApSvMg"),
			Arguments.of("https://www.youtube.com/watch?v=_5BTo2oZ0SE", "_5BTo2oZ0SE"),
			Arguments.of("https://m.youtube.com/watch?feature=youtu.be&v=_5BTo2oZ0SE", "_5BTo2oZ0SE"),
			Arguments.of("https://m.youtube.com/watch?v=_5BTo2oZ0SE", "_5BTo2oZ0SE"),
			Arguments.of("https://www.youtube.com/watch?feature=youtu.be&v=_5BTo2oZ0SE&app=desktop", "_5BTo2oZ0SE"),
			Arguments.of("https://www.youtube.com/watch?v=nBuae6ilH24", "nBuae6ilH24"),
			Arguments.of("https://www.youtube.com/watch?v=eLlxrBmD3H4", "eLlxrBmD3H4"),
			Arguments.of("https://www.youtube.com/watch?v=DFYRQ_zQ-gk&feature=featured", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://m.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://m.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://m.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://m.youtube.com/watch?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtube.com/v/DFYRQ_zQ-gk?fs=1&hl=en_US", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/embed/DFYRQ_zQ-gk?autoplay=1", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://www.youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtube.com/embed/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtu.be/DFYRQ_zQ-gk?t=120", "DFYRQ_zQ-gk"),
			Arguments.of("https://youtu.be/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtu.be/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtu.be/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("http://youtu.be/DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/HamdiKickProduction?v=DFYRQ_zQ-gk", "DFYRQ_zQ-gk"),
			Arguments.of("https://www.youtube.com/watch?v=wz4MLJBdSpw&t=67s", "wz4MLJBdSpw"),
			Arguments.of("http://www.youtube.com/watch?v=dQw4w9WgXcQ&a=GxdCwVVULXctT2lYDEPllDR0LRTutYfW", "dQw4w9WgXcQ"),
			Arguments.of("http://www.youtube.com/embed/dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("http://www.youtube.com/watch?v=dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("https://www.youtube.com/watch?v=EL-UCUAt8DQ", "EL-UCUAt8DQ"),
			Arguments.of("http://www.youtube.com/watch?v=dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("http://youtu.be/dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("http://www.youtube.com/v/dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("http://www.youtube.com/e/dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("http://www.youtube.com/watch?feature=player_embedded&v=dQw4w9WgXcQ", "dQw4w9WgXcQ"),
			Arguments.of("https://www.youtube-nocookie.com/embed/xHkq1edcbk4?rel=0", "xHkq1edcbk4")
		);
	}
}
